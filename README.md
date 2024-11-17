# CampusWallet

**Campus Wallet** is a Java-based application designed to help university students manage their finances. The project provides a set of tools for managing financial transactions, including features such as sending/adding money, currency conversion, loan calculation, and notifications.

## Features

- **User Authentication**: Users can create accounts, log in, and manage their personal information.
- **Account Management**: View account details, transactions, and balance.
- **Transaction Management**: Send money, view transaction history, and manage transaction records.
- **Currency Conversion**: Convert between different currencies.
- **Loan Calculator**: Calculate loan payments based on interest rates and loan duration.
- **Notifications**: Get notifications for important account updates.

## Technologies Used

- **Java**: The core language used for backend development.
- **JavaFX**: Used for building the user interface (UI).
- **MySQL**: Database used for storing user data, transactions, and other necessary information.
- **Maven**: For project management and dependency management.

## Installation

### Prerequisites

- **Java 11** or later
- **MySQL** database
- **Maven** (if you wish to build the project yourself)

### Steps to Run the Project Locally

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/sayad-dot/CampusWallet.git
2.Navigate to the project directory:
bash
Copy code
cd CampusWallet

3.Set up the MySQL database:

Create a MySQL database called campuswallet.
Update the DatabaseConnection.java file with your MySQL credentials.

4.Build and run the project using your IDE (e.g., IntelliJ IDEA, Eclipse) or via Maven:

If using Maven, run the following command:
bash
Copy code
mvn clean install

5.Run the JavaFX application:

If using IntelliJ IDEA, right-click on the Main.java file and select Run.
If using the command line, run:
bash
Copy code
java -jar target/CampusWallet.jar


Contributing
We welcome contributions to improve the project! To contribute:
i.Fork the repository.
ii.Create a new branch for your changes.
iii.Make your changes and commit them with clear messages.
iv.Open a pull request to merge your changes into the main branch.

License
This project is open source and available under the MIT License.


