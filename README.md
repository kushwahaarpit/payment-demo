# End-to-End Payment Processing Workflow - BDD Test Suite

This project simulates and validates an end-to-end payment processing system using BDD (Behavior-Driven Development) with **Cucumber**, **Java**. The system verifies each step in a typical payment lifecycle including qualification, sanction screening, fraud detection, funds availability, and clearing house validation.

---

## 🧪 Features Tested

Each payment transaction goes through the following checks:

1. ✅ **Qualification Check** – Validates if the debtor account is active and required details are present.
2. 🚫 **Sanction Screening** – Checks if the creditor is on the restricted (sanctioned) list.
3. ⚠️ **Fraud Detection** – Checks if the creditor's account number is flagged as fraudulent.
4. 💰 **Fund Availability** – Ensures the debtor account has sufficient balance.
5. 🏦 **Clearing House Check** – Validates if the clearing house is active and matches expectations.
6. 📊 **Transaction Evaluation** – Final transaction success or failure based on all prior checks.

---

## ✅ Scenarios Covered

| Scenario               | Description                                |
|------------------------|--------------------------------------------|
| All checks passed      | Valid transaction from start to finish     |
| Qualification failed   | Debtor account inactive                    |
| Sanction failed        | Creditor present in sanction list          |
| Fraud failed           | Creditor account marked as fraudulent      |
| Fund failed            | Debtor lacks sufficient balance            |
| Clearing failed        | Clearing house is not active or mismatched |

---

## 🔧 Tech Stack

- Java 17+
- Maven
- Cucumber (Gherkin syntax)
- VS Code

---

## ▶️ How to Run

1. **Clone the project:**
   ```
    git clone https://github.com/kushwahaarpit/payment-demo.git
    cd payment-processing-bdd
   ```

2. **Clone the project:**
    Use your IDE's test runner or:
    ```
    mvn test

    ```

