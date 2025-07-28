Feature: End-to-End Payment Processing Workflow

  Background:
    Given the sanction list is:
      | Arpit        |
      | Blacklisted  |
      | Terror Org   |
    And the fraud list is:
      | 1234567890   |
      | 9999999999   |

  Scenario Outline: End-to-End Payment Processing
    Given the debtor account is "<isActive>"
    And the debtor account balance is "<debtorBalance>"
    And the creditor name is "<creditorName>"
    And the creditor account number is "<creditorAccountNumber>"
    And the amount is "<amount>"
    And the clearing house name is "<clearingHouseName>"
    And the clearing house status is "<clearingHouseStatus>"
    When the payment is initiated
    Then check qualification status for debtor account "<isActive>" with clearing house "<clearingHouseName>", amount "<amount>", creditor name "<creditorName>", creditor account number "<creditorAccountNumber>"
    Then sanction check for creditor "<creditorName>"
    Then fraud check for creditor account number "<creditorAccountNumber>"
    Then funds are available in debtor account "<debtorAccountNumber>"
    Then clearing house check for "<clearingHouseName>" with status "<clearingHouseStatus>"
    Then transaction status should be evaluated

  Examples:
    | isActive | debtorBalance | clearingHouseName  | clearingHouseStatus | creditorName | creditorAccountNumber  | amount   | debtorAccountNumber  |
    | "true"   | "5000.00"     | "RTGS"             | "ACTIVE"            | "Rahul"      | "1231231234"           | "1000.00"| "9876543210"         | # All Pass
    | "true"   | "6000.00"     | "NEFT"             | "ACTIVE"            | "Arpit"      | "1234567891"           | "1000.00"| "9876543210"         | # Sanction Fail
    | "true"   | "4000.00"     | "RTGS"             | "ACTIVE"            | "Aman"       | "1234567890"           | "3000.00"| "9876543210"         | # Fraud Fail
    | "true"   | "100.00"      | "IMPS"             | "ACTIVE"            | "Rohit"      | "1010101010"           | "500.00" | "9876543210"         | # Fund Fail
    | "false"  | "3000.00"     | "NEFT"             | "INACTIVE"          | "Dev"        | "2020202020"           | "200.00" | "9876543210"         | # Qualification Fail
    | "true"   | "5000.00"     | "RTGS"             | "INACTIVE"          | "Suresh"     | "1111111111"           | "1000.00"| "9876543210"         | # Clearing Fail
