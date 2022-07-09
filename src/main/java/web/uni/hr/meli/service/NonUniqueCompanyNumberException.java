package web.uni.hr.meli.service;

public class NonUniqueCompanyNumberException extends RuntimeException{

    public NonUniqueCompanyNumberException(String companyNumber) {
        super("Existingg company number: "+companyNumber);
    }
}
