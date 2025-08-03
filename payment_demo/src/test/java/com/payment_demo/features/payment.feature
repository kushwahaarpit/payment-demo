Feature: Basic Payment Flow with Validations

  Background:
    Given the sanction list is:
      | John Doe   |
      | BannedUser |
    And the fraud list is:
      | 1234567890 |
      | 9876543210 |

  Scenario Outline: Process payment with various flow conditions
    Given the debtor account is "<debtor_status>"
    And the debtor account balance is "<debtor_balance>"
    And the transfer amount is "<amount>"
    And the creditor name is "<creditor_name>"
    And the creditor account number is "<creditor_account_number>"
    When the payment is initiated
    Then perform sanction check
    Then perform fraud check
    Then perform fund check
    Then evaluate transaction status

    Examples:
      | debtor_status | debtor_balance | amount | creditor_name | creditor_account_number |
      | active        | 10000          | 5000   | Alice Kumar   | 1111222233              |
      | inactive      | 10000          | 5000   | Alice Kumar   | 1111222233              |
      | active        | 8000           | 2000   | John Doe      | 1111222233              |
      | active        | 9000           | 1000   | Alice Kumar   | 1234567890              |
      | active        | 4000           | 5000   | Alice Kumar   | 1111222233              |
