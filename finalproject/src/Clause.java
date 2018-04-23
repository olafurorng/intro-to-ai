
public class Clause {

    private String originalForm;
    private String cnfForm;

    public Clause(String originalForm, String cnfForm) {
        this.originalForm = originalForm;
        this.cnfForm = cnfForm;


    }

    public int getOriginalForm() {
        return originalForm;
    }

    public int getCnfForm() {
        return cnfForm;
    }


}
