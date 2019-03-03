package org.demonfox.mypoem;

import com.microsoft.azure.storage.table.TableServiceEntity;

public class PoemIndexTableEntity extends TableServiceEntity {
    private static final String PoemIndexTablePartitionKey = "";

    public PoemIndexTableEntity(String id) {
        this.partitionKey = PoemIndexTablePartitionKey;
        this.rowKey = id;
    }

    public PoemIndexTableEntity() { }

    private String author;
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
