
package org.alfresco.po.share.user;

import static org.alfresco.po.RenderElement.getVisibleRenderElement;

import org.alfresco.po.RenderTime;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

/**
 * When the users selects Delete of many items in the trashcan they will be
 * presented with confirmation about the list of items Delete. This page is
 * validate the confirmation dialog.
 * 
 * @author Subashni Prasanna
 * @since 1.7.0
 */

public class TrashCanDeleteConfirmDialog extends TrashCanPage
{
    protected static final By DELETE_OK_BUTTON = By.cssSelector("div.ft button");

    @SuppressWarnings("unchecked")
    public TrashCanDeleteConfirmDialog render(RenderTime timer)
    {
        try
        {
            elementRender(timer, getVisibleRenderElement(DELETE_OK_BUTTON));
        }
        catch (NoSuchElementException e)
        {
        }
        catch (TimeoutException e)
        {
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TrashCanDeleteConfirmDialog render()
    {
        return render(new RenderTime(maxPageLoadingTime));
    }

    /**
     * This method helps to click on OK button
     */
    public TrashCanPage clickDeleteOK()
    {
        driver.findElement(DELETE_OK_BUTTON).click();
        return factoryPage.instantiatePage(driver, TrashCanPage.class);
    }
}
