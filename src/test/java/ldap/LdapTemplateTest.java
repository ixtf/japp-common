package ldap;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.AttributesMapperCallbackHandler;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.NotFilter;
import org.springframework.ldap.filter.PresentFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.ldap.transaction.compensating.manager.TransactionAwareContextSourceProxy;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jzb on 16-6-9.
 */
public class LdapTemplateTest {
    private static LdapTemplate ldapTemplate;
    private static AttributesMapper<Attributes> mapper = t -> t;

    static {
        LdapContextSource contextSourceTarget = new LdapContextSource();
        contextSourceTarget.setCacheEnvironmentProperties(false);
        contextSourceTarget.setUrl("ldap://192.168.18.180:389");
        contextSourceTarget.setBase("dc=hengyi,dc=com");
        contextSourceTarget.setUserDn("cn=weixin,cn=users,dc=hengyi,dc=com");
        contextSourceTarget.setPassword("123456");
        contextSourceTarget.setAuthenticationSource(new AuthenticationSource() {
            @Override
            public String getCredentials() {
                return "123456";
            }

            @Override
            public String getPrincipal() {
                return "cn=weixin,cn=users,dc=hengyi,dc=com";
            }
        });
        TransactionAwareContextSourceProxy contextSource = new TransactionAwareContextSourceProxy(contextSourceTarget);
        ldapTemplate = new LdapTemplate(contextSource);
    }

    public static Attributes ldapLookup_hr(String cn) throws InvalidNameException {
        return ldapTemplate.lookup(LdapUtils.newLdapName("ou=hr").add("cn=" + cn), mapper);
    }

    public static Attributes ldapLookup_oa(String cn) throws InvalidNameException {
        return ldapTemplate.lookup(LdapUtils.newLdapName("ou=oa").add("cn=" + cn), mapper);
    }

    public static void main(String[] args) throws InvalidNameException {
        Attributes attrs = ldapLookup_oa("yangll");
        System.out.println(attrs);
    }


    //    @Test
    public void test() throws NamingException, IOException {
        Filter filter = new EqualsFilter("employeeid", "12000077");
        LdapQuery ldapQuery = LdapQueryBuilder.query().base("ou=oa").filter(filter);
        List<Attribute> attrs = ldapTemplate.search(ldapQuery, new AttributesMapper<Attribute>() {
            @Override
            public Attribute mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes.get("cn");
            }
        });
        Attribute attr = CollectionUtils.isEmpty(attrs) ? null : attrs.get(0);
        System.out.println(attr);

        Filter noHrFilter = new NotFilter(new PresentFilter("employeeid"));
        AtomicInteger noHrCount = new AtomicInteger(0);
        ldapTemplate.search(LdapQueryBuilder.query().base("ou=oa").countLimit(100).filter(noHrFilter), new AttributesMapper<Object>() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                noHrCount.incrementAndGet();
                return null;
            }
        });
        System.out.println(noHrCount);
    }

    @Test
    public void pageTest() throws NamingException, IOException {
        Name name = LdapUtils.newLdapName("ou=oa");
        String filter = new NotFilter(new PresentFilter("employeeid")).encode();
        SearchControls searchControls = new SearchControls();
        AttributesMapperCallbackHandler<Attributes> handler = new AttributesMapperCallbackHandler(mapper);
        int pageSize = 500;
        PagedResultsDirContextProcessor processor = new PagedResultsDirContextProcessor(pageSize);
        do {
            ldapTemplate.search(name, filter, searchControls, handler, processor);
        } while (processor.hasMore());
        System.out.println(handler.getList().size());
    }
}
