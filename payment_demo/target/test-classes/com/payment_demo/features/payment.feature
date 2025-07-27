Feature: End-to-End Payment Processing Workflow

  Background:
    Given the sanction list is:
      | Arpit        |
      | Blacklisted  |
      | Terror Org   |
    And the fraud list is:
      | 1234567890   |
      | 9999999999   |

  Scenario Outline: 1 - Payment is initiated
    Given the debtor account is <isActive>
    And the debtor account balance is <debtorBalance>
    And the creditor name is "<creditorName>"
    And the creditor account number is <creditorAccountNumber>
    And the amount is <amount>
    When the payment is initiated

  Scenario Outline: 2 - Qualification of Payment
    Then check qualification status for debtor account <isActive> with clearing house "<clearingHouseName>", amount <amount>, creditor name "<creditorName>", creditor account number <creditorAccountNumber>

  Scenario Outline: 3 - Sanction Check
    Then sanction check for creditor "<creditorName>"

  Scenario Outline: 4 - Fraud Check
    Then fraud check for creditor account number <creditorAccountNumber>

  Scenario Outline: 5 - Funds Check
    Then funds are available in debtor account <debtorAccountNumber>

  Scenario Outline: 6 - Clearing House Check
    Given the clearing house name is "<clearingHouseName>"
    And the clearing house status is "<clearingHouseStatus>"
    Then clearing house check for "<clearingHouseName>" with status "<clearingHouseStatus>"

  Scenario Outline: 7 - Transaction Status Evaluation
    Then transaction status should be evaluated

  Examples:
    | isActive | debtorBalance | clearingHouseName | clearingHouseStatus | creditorName | creditorAccountNumber | amount | debtorAccountNumber |
    | true     | 5000.00       | RTGS              | ACTIVE              | Rahul        | 1231231234            | 1000.0 | 9876543210          |
    | true     | 6000.00       | NEFT              | ACTIVE              | Arpit        | 1234567891            | 1000.0 | 9876543210          |
    | true     | 4000.00       | RTGS              | ACTIVE              | Aman         | 1234567890            | 3000.0 | 9876543210          |
    | true     | 100.00        | IMPS              | ACTIVE              | Rohit        | 1010101010            | 500.0  | 9876543210          |
    | false    | 3000.00       | NEFT              | INACTIVE            | Dev          | 2020202020            | 200.0  | 9876543210          |
