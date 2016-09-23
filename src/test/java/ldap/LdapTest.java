package ldap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by jzb on 16-5-16.
 */
public class LdapTest {
    private static LdapContext ctx;
    private static String ldap_basedn = "OU=oa,DC=hengyi,DC=com";

    static {
        String ldap_url = "ldap://192.168.18.180:389";
        String ldap_user = "CN=weixin,CN=Users,DC=hengyi,DC=com";
        String ldap_pwd = "123456";

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldap_user);
        env.put(Context.SECURITY_CREDENTIALS, ldap_pwd);
        env.put(Context.PROVIDER_URL, ldap_url);

        try {
            ctx = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws NamingException, IOException {
        Multimap<String, Object> hasPassMap = ArrayListMultimap.create();
        int hasPassCount = 0;
        Multimap<String, Object> noHrMap = ArrayListMultimap.create();
        int noHrCount = 0;
        Multimap<String, Object> hasPassMap_string = ArrayListMultimap.create();
        int hasPassCount_string = 0;

        BasicAttributes basicattrs = new BasicAttributes();
        int totalCount = 0;
        int pageSize = 100;
        byte[] cookie = null;
        Control[] ctls = new Control[]{new PagedResultsControl(pageSize, true)};
        ctx.setRequestControls(ctls);
        do {
            NamingEnumeration<SearchResult> results = ctx.search(ldap_basedn, basicattrs);
            while (results != null && results.hasMoreElements()) {
                totalCount++;

                SearchResult entry = results.nextElement();
                Attributes attrs = entry.getAttributes();
                String cn = (String) attrs.get("cn").get();
                Attribute password = attrs.get("userpassword");
                if (password != null) {
                    hasPassCount++;
                    hasPassMap.put(cn, attrs.get("name").get());

                    byte[] bits = (byte[]) password.get();
                    if (bits.length == 32) {
                        hasPassCount_string++;
                        hasPassMap_string.put(cn, new String(bits));
                    }
                }
                Attribute employeeid = attrs.get("employeeid");
                if (employeeid == null) {
                    noHrCount++;
                    noHrMap.put(cn, attrs.get("name").get());
                }
            }
            cookie = parseControls(ctx.getResponseControls());
            ctx.setRequestControls(new Control[]{new PagedResultsControl(pageSize, cookie, Control.CRITICAL)});
        } while ((cookie != null) && (cookie.length != 0));
        System.out.println("totalCount=" + totalCount);
        System.out.println("hasPassCount_string=" + hasPassCount_string);
        System.out.println(hasPassMap_string);
        System.out.println("hasPassMap=" + hasPassCount);
        System.out.println(hasPassMap);
        System.out.println("noHrMap=" + noHrCount);
        System.out.println(noHrMap);
    }

    static byte[] parseControls(Control[] controls) throws NamingException {
        byte[] cookie = null;
        if (controls != null) {
            for (int i = 0; i < controls.length; i++) {
                if (controls[i] instanceof PagedResultsResponseControl) {
                    PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
                    cookie = prrc.getCookie();
//                    System.out.println(">>Next Page \n");
                }
            }
        }
        return (cookie == null) ? new byte[0] : cookie;
    }

    //    @Test
    public void test() throws NamingException, IOException {
        BasicAttributes basicattrs = new BasicAttributes();
//        basicattrs.put("cn", "jzb");
        NamingEnumeration<SearchResult> sr = ctx.search(ldap_basedn, basicattrs);
        if (sr.hasMore()) {
            SearchResult entry = sr.nextElement();
            Attributes attrs = entry.getAttributes();
            Attribute cn = attrs.get("cn");
            Attribute corp = attrs.get("company");
            Attribute depart = attrs.get("department");
            Attribute name = attrs.get("displayname");
            Attribute oauser = attrs.get("oauser");
            Attribute mobile = attrs.get("mobile");
            if (mobile == null) {
                mobile = new BasicAttribute("mobile");
                mobile.add(0, "tele-num");
            }
            String info = String.format("%s:%s:%s:%s", cn.get(0).toString()
                    , corp.get(0).toString(), depart.get(0).toString(), name.get(0).toString()
                    , mobile.get(0).toString());
            System.out.println(info);
        }
        ctx.close();
    }

}
