package crm.framework.db;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryResult {
    private List<Map<String, String>> result = new ArrayList();

    public QueryResult() {
    }

    public String getValue(String label, int index) {
        if (this.result.size() <= index) {
            return null;
        } else {
            Map<String, String> row = (Map)this.result.get(index);
            return (String)row.get(label);
        }
    }

    public List<String> getAllValues(String label) {
        label = label.toUpperCase();
        List<String> list = new ArrayList();
        if (this.result.isEmpty()) {
            return list;
        } else {
            Iterator iterator = this.result.iterator();

            while(iterator.hasNext()) {
                Map<String, String> map = (Map)iterator.next();
                list.add(map.get(label));
            }

            return list;
        }
    }

    public String getValueFromCondition(String selectField, String fromField, String matchCondition) {
        Iterator iterator = this.result.iterator();

        Map map;
        do {
            if (!iterator.hasNext()) {
                throw new ElementNotFoundException(selectField, "", "");
            }

            map = (Map)iterator.next();
        } while(!matchCondition.equals(map.get(fromField)));

        return (String)map.get(selectField);
    }

    public String getValueFromFirstRow(String label) {
        if (this.result.isEmpty()) {
            return null;
        } else {
            Map<String, String> row = (Map)this.result.get(0);
            return row.get(label) == null ? "" : (String)row.get(label);
        }
    }

    public Map<String, String> getRow(int index) {
        if (this.result.size() <= index) {
            return null;
        } else {
            Map<String, String> row = (Map)this.result.get(index);
            return row;
        }
    }

    public int numOfResult() {
        return this.result.size();
    }

    public void enqueRow(Map<String, String> row) {
        this.result.add(row);
    }

    public boolean isExpectedResult(String columnLabel, String expectedResult) {
        if (this.numOfResult() != 1) {
            return false;
        } else {
            String actualRes = this.getValueFromFirstRow(columnLabel);
            return actualRes == null ? false : actualRes.equals(expectedResult);
        }
    }

    public boolean isOnlyOneRecord() {
        return this.numOfResult() == 1;
    }

    public int getResultSize() {
        return this.result.size();
    }

    public List<Map<String, String>> getResult() {
        return this.result;
    }

}
