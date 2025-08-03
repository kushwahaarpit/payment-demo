package com.payment_demo.stepDefination;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class stepDefination {

    private boolean isAccountActive;
    private double debtorBalance;
    private double transferAmount;
    private String creditorName;
    private long creditorAccountNumber;

    private List<String> sanctionList;
    private List<Long> fraudList;

    private boolean qualificationPassed;
    private boolean sanctionPassed;
    private boolean fraudPassed;
    private boolean fundPassed;
    private boolean transactionCompleted;

    @Given("the debtor account is {string}")
    public void theDebtorAccountIs(String status) {
        isAccountActive = status.equalsIgnoreCase("active");
    }

    @And("the debtor account balance is {string}")
    public void theDebtorAccountBalanceIs(String balance) {
        debtorBalance = Double.parseDouble(balance);
    }

    @And("the transfer amount is {string}")
    public void theTransferAmountIs(String amount) {
        transferAmount = Double.parseDouble(amount);
    }

    @And("the creditor name is {string}")
    public void theCreditorNameIs(String name) {
        creditorName = name;
    }

    @And("the creditor account number is {string}")
    public void theCreditorAccountNumberIs(String accountNumber) {
        creditorAccountNumber = Long.parseLong(accountNumber);
    }

    @Given("the sanction list is:")
    public void theSanctionListIs(DataTable table) {
        sanctionList = table.asList();
    }

    @Given("the fraud list is:")
    public void theFraudListIs(DataTable table) {
        fraudList = table.asList().stream().map(Long::parseLong).collect(Collectors.toList());
    }

    @When("the payment is initiated")
    public void thePaymentIsInitiated() {
        if (!isAccountActive) {
            qualificationPassed = false;
            Assert.fail("Debtor account is not active");
        } else {
            qualificationPassed = true;
        }
    }

    @Then("perform sanction check")
    public void performSanctionCheck() {
        if (!qualificationPassed) {
            sanctionPassed = false;
            Assert.fail("Skipping sanction check: qualification failed");
            return;
        }

        if (sanctionList.contains(creditorName)) {
            sanctionPassed = false;
            Assert.fail("Sanction check failed for " + creditorName);
        } else {
            sanctionPassed = true;
        }
    }

    @Then("perform fraud check")
    public void performFraudCheck() {
        if (!sanctionPassed) {
            fraudPassed = false;
            Assert.fail("Skipping fraud check: sanction check failed");
            return;
        }

        if (fraudList.contains(creditorAccountNumber)) {
            fraudPassed = false;
            Assert.fail("Fraud detected for account: " + creditorAccountNumber);
        } else {
            fraudPassed = true;
        }
    }

    @Then("perform fund check")
    public void performFundCheck() {
        if (!fraudPassed) {
            fundPassed = false;
            Assert.fail("Skipping fund check: fraud check failed");
            return;
        }

        if (debtorBalance >= transferAmount) {
            fundPassed = true;
        } else {
            fundPassed = false;
            Assert.fail("Insufficient funds");
        }
    }

    @Then("evaluate transaction status")
    public void evaluateTransactionStatus() {
        if (qualificationPassed && sanctionPassed && fraudPassed && fundPassed) {
            transactionCompleted = true;
            System.out.println("✅ Transaction completed successfully.");
        } else {
            transactionCompleted = false;
            Assert.fail("❌ Transaction failed.");
        }
    }
}
