package org.demonfox.mypoem;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.*;

import java.security.InvalidKeyException;

public class PoemTableQuery {
    private String storageConnectionString =
            "";

    private String poemText = "";
    public Poem addNewPoem() {

        Poem poem = new Poem();
        poem.setTitle("泊船瓜洲");
        poem.setDynasty("【宋】");
        poem.setAuthor("王安石");
        poem.setFullText("京口瓜洲一水间，钟山只隔数重山。\n" +
                "春风又绿江南岸，明月何时照我还。");

        try {
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            // first, determine the index which we will be associating the new poem with
            TableOperation retrieveOperation
                    = TableOperation.retrieve(PoemConfigTableEntity.PoemConfigTablePartitionKey,
                    PoemConfigTableEntity.PoemConfigMaxIndex, PoemConfigTableEntity.class);

            CloudTable cloudTable = tableClient.getTableReference("MyPoemConfig");
            PoemConfigTableEntity poemConfigMaxIndex = cloudTable.execute(retrieveOperation).getResultAsType();

            int maxIndex = 0;
            if (poemConfigMaxIndex != null)
                maxIndex = poemConfigMaxIndex.getConfigValue() + 1;
            else
                throw new InvalidKeyException("Invalid configurable key: " + PoemConfigTableEntity.PoemConfigMaxIndex);

            // second, insert the index for the new poem
            PoemIndexTableEntity poemIndexTableEntity = new PoemIndexTableEntity(Integer.toString(maxIndex));
            poemIndexTableEntity.setAuthor(poem.getAuthor());
            poemIndexTableEntity.setTitle(poem.getTitle());

            cloudTable = tableClient.getTableReference("MyPoemIndex");
            TableOperation insertPoemIndex = TableOperation.insertOrReplace(poemIndexTableEntity);
            cloudTable.execute(insertPoemIndex);

            // third, insert the new poem
            PoemTableEntity poemTableEntity = new PoemTableEntity(poem.getAuthor(), poem.getTitle());
            poemTableEntity.setTitle(poem.getTitle());
            poemTableEntity.setDynasty(poem.getDynasty());
            poemTableEntity.setAuthor(poem.getAuthor());
            poemTableEntity.setFullText(poem.getFullText());

            cloudTable = tableClient.getTableReference("MyPoem");
            TableOperation insertPoem = TableOperation.insertOrReplace(poemTableEntity);
            cloudTable.execute(insertPoem);

            // fourth, update the max index
            poemConfigMaxIndex.setConfigValue(maxIndex);

            cloudTable = tableClient.getTableReference("MyPoemConfig");
            TableOperation replaceEntity = TableOperation.replace(poemConfigMaxIndex);
            cloudTable.execute(replaceEntity);

//            for (String table : tableClient.listTables()) {
//                result.append(table);
        }
        catch (Exception e) {
            poem.setFullText(e.toString());
        }

        return poem;
    }

    public void getRandomPoem(Poem p) {
    }
    public String getPoemText() {
        return poemText;
    }
}
