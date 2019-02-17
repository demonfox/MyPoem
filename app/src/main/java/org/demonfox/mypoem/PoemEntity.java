package org.demonfox.mypoem;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.*;

/**
 * Created by Y3 on 6/14/18.
 */

public class PoemEntity extends TableServiceEntity {
    private static final String PoemTablePartitionKey = "";

    public PoemEntity(String id) {
        this.partitionKey = PoemTablePartitionKey;
        this.rowKey = id;
    }

    public PoemEntity() { }

    private String title;
    private String author;
    private String dynasty;
    private String fullText;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }
}
