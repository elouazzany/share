package org.alfresco.po.share.bulkimport;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Calendar;

import org.alfresco.po.HtmlPage;
import org.alfresco.po.RenderTime;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

/**
 * @author Sergey Kardash
 */
public class InPlaceBulkImportPage extends AdvancedBulkImportPage
{
    private org.apache.commons.logging.Log logger = LogFactory.getLog(InPlaceBulkImportPage.class);

    // Input fields
    private static final By CONTENT_STORE = By.cssSelector("input[name$='contentStore']");

    @SuppressWarnings("unchecked")
    @Override
    public InPlaceBulkImportPage render(RenderTime timer)
    {
        basicRender(timer);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public InPlaceBulkImportPage render()
    {
        return render(new RenderTime(maxPageLoadingTime));
    }

    /**
     * Method to set import directory field
     * 
     * @param contentStore String
     */
    public void setContentStoreField(final String contentStore)
    {
        setInput(findAndWait(CONTENT_STORE), contentStore);
    }

    /**
     * Method for bulk import creation
     * 
     * @param importDirectory String
     * @param contentStore String
     * @param path String
     * @param disableRules boolean
     * @return StatusBulkImportPage
     */
    public HtmlPage createImportInPlace(String importDirectory, String contentStore, String path, boolean disableRules)
    {
        logger.info("Create import");
        try
        {

            if (importDirectory != null && !importDirectory.isEmpty())
            {
                setImportDirectoryField(importDirectory);
            }
            else
            {
                setImportDirectoryField(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            }
            if (contentStore != null && !contentStore.isEmpty())
            {
                setContentStoreField(contentStore);
            }
            else
            {
                setContentStoreField("default");
            }
            if (path != null && !path.isEmpty())
            {
                setTargetPathField(path);
            }
            if (disableRules)
            {
                setDisableRulesCheckbox();
            }
            clickImport();
        }
        catch (TimeoutException te)
        {
            logger.debug("The operation has timed out");
        }
        catch (NoSuchElementException nse)
        {
            logger.debug("Unable to find the elements");
        }

        waitUntilElementDeletedFromDom(INITIATE_BULK_IMPORT_BUTTON, SECONDS.convert(maxPageLoadingTime, MILLISECONDS));
        waitUntilElementPresent(IN_PROGRESS_CURRENT_STATUS, SECONDS.convert(maxPageLoadingTime, MILLISECONDS));
        waitUntilElementPresent(IDLE_CURRENT_STATUS, (SECONDS.convert(maxPageLoadingTime, MILLISECONDS))*2);
        return getCurrentPage();
    }
}
