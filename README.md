CampusWallet
CampusWallet is an Online Banking Management desktop application built using Java, JavaFX SceneBuilder, and MySQL.
It allows users to manage their banking activities easily, including account management, money transfers, loan calculations, currency conversions, and activity tracking.

Features
User Registration and Login
Users can create a new account by providing their name, ID, password, email, date of birth, phone number, and initial balance. After registration, users can log in using their email and password.

Account Details
View all personal account details after logging in.

Send Money
Transfer money to another user by providing the recipient’s ID, amount, and password for confirmation.

Loan Calculator
Calculate monthly loan payments based on loan amount, interest rate, and repayment period.

Currency Converter
Convert between different currencies in real time.

Audit Log
View the complete history of all actions performed during the session.

Technologies Used

Java

JavaFX (SceneBuilder)

MySQL

Database Structure

Users Table — Stores user details like ID, Name, Password, Email, Phone, DOB, and Balance.

Transactions Table — Records all transaction activities between users.

Audit Log Table — Tracks all actions performed by users.

Setup Instructions
Clone this repository.

Set up a MySQL database using the provided schema.

Update the database connection details in the project files (like host, username, password).

Open the project in your preferred Java IDE.

Use JavaFX SceneBuilder to modify UI if needed.

Run the project.

Author-Sayad Ibne Azad
