package tfg.k_lendar.http.models.helper;

public class TaskTruancySimple {
    String elementType;
    String title;
    String moduleId;
    String ufId;
    String ruleId;
    String elementId;

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    String dueDate;

    /* Task constructor */
    public TaskTruancySimple(String elementType, String title, String moduleId, String ufId, String ruleId, String elementId, String dueDate) {
        this.elementType = elementType;
        this.title = title;
        this.moduleId = moduleId;
        this.ufId = ufId;
        this.ruleId = ruleId;
        this.elementId = elementId;
        this.dueDate = dueDate;
    }

    /* Truancy constructor */
    public TaskTruancySimple(String elementType, String title, String moduleId, String ufId, String elementId, String dueDate) {
        this.elementType = elementType;
        this.title = title;
        this.moduleId = moduleId;
        this.ufId = ufId;
        this.elementId = elementId;
        this.dueDate = dueDate;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getUfId() {
        return ufId;
    }

    public void setUfId(String ufId) {
        this.ufId = ufId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }
}
