package org.demonfox.mypoem;

import com.microsoft.azure.storage.table.TableServiceEntity;

public class PoemConfigTableEntity extends TableServiceEntity {
    public static final String PoemConfigTablePartitionKey = "";
    public static final String PoemConfigMaxIndex = "MaxIndex";

    public PoemConfigTableEntity(String id) {
        this.partitionKey = PoemConfigTablePartitionKey;
        this.rowKey = id;
    }

    public PoemConfigTableEntity() { }

    private int configValue;

    public int getConfigValue() {
        return configValue;
    }

    public void setConfigValue(int val) {
        this.configValue = val;
    }
}
