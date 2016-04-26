
package org.customer;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;

/**
 * Base class for all example customer tag implementations.
 * 
 * @author gavinc
 */
public class AbstractCustomerTag extends TagSupport
{
    private static final long serialVersionUID = -4229345855362757730L;
    private static final String PARAM_REPO_HOST = "org.customer.alfresco.host";
    private static final String PARAM_REPO_PORT = "org.customer.alfresco.port";
    private static final String PARAM_REPO_CONTEXT = "org.customer.alfresco.context";
    private static final String PARAM_REPO_USERNAME = "org.customer.alfresco.username";
    private static final String PARAM_REPO_PASSWORD = "org.customer.alfresco.password";

    private String nodeRef;

    /**
     * Returns the nodeRef of the content to be displayed
     * 
     * @return NodeRef
     */
    public String getNodeRef()
    {
        return this.nodeRef;
    }

    /**
     * Sets the NodeRef of the content to be displayed
     * 
     * @param nodeRef NodeRef
     */
    public void setNodeRef(String nodeRef)
    {
        this.nodeRef = nodeRef;
    }

    protected String getRepoHost()
    {
        String repoHost = this.pageContext.getServletContext().getInitParameter(PARAM_REPO_HOST);
        if (repoHost == null || repoHost.length() == 0)
        {
            repoHost = "localhost";
        }

        return repoHost;
    }

    protected int getRepoPort()
    {
        int repoPort = 8080;

        String repoPortParam = this.pageContext.getServletContext().getInitParameter(PARAM_REPO_PORT);
        if (repoPortParam != null && repoPortParam.length() > 0)
        {
            try
            {
                repoPort = Integer.parseInt(repoPortParam);
            }
            catch (NumberFormatException nfe)
            {
                repoPort = 8080;
            }
        }

        return repoPort;
    }

    protected String getRepoContext()
    {
        String repoContext = this.pageContext.getServletContext().getInitParameter(PARAM_REPO_CONTEXT);
        if (repoContext == null || repoContext.length() == 0)
        {
            repoContext = "alfresco";
        }

        return repoContext;
    }

    protected String getRepoUsername()
    {
        String repoUser = this.pageContext.getServletContext().getInitParameter(PARAM_REPO_USERNAME);
        if (repoUser == null || repoUser.length() == 0)
        {
            repoUser = "admin";
        }

        return repoUser;
    }

    protected String getRepoPassword()
    {
        String repoPwd = this.pageContext.getServletContext().getInitParameter(PARAM_REPO_PASSWORD);
        if (repoPwd == null || repoPwd.length() == 0)
        {
            repoPwd = "admin";
        }

        return repoPwd;
    }

    protected String getRepoUrl()
    {
        return "http://" + getRepoHost() + ":" + getRepoPort() + "/" + getRepoContext();
    }

    protected HttpClient getHttpClient()
    {
        HttpClient client = new HttpClient();

        client.getParams().setAuthenticationPreemptive(true);
        client.getState().setCredentials(new AuthScope(getRepoHost(), getRepoPort()),
                    new UsernamePasswordCredentials(getRepoUsername(), getRepoPassword()));

        return client;
    }

    @Override
    public void release()
    {
        super.release();

        this.nodeRef = null;
    }
}
