package org.demonfox.mypoem;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.*;
/**
 * Created by Y3 on 6/13/18.
 */

public class PoemTableQuery extends Thread {
    private String storageConnectionString =
            "DefaultEndpointsProtocol=https;"
                    + "AccountName=demonfox;"
                    + "AccountKey=CgbAyUOgsxjxXH7gDvyFPIA9xzdI2QHbZPRzZf6WM+zaCGXL38oc3fHrWyRgwLxu4NAXEJcUtPKjVh+oghBGaw==;"
                    + "EndpointSuffix=core.windows.net";

    private String poemText = "";
    public void run() {
        StringBuilder result = new StringBuilder();
        try {
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            for (String table : tableClient.listTables()) {
                result.append(table);
            }
        }
        catch (Exception e) {
            result.append(e.toString());
        }

        poemText = result.toString();
    }

    public String getPoemText() {
        return poemText;
    }
}
