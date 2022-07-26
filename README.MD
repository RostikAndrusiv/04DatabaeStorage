#Task 7
Description
Please, complete the following task

1 point.

Implement the next use cases of File Share application:

Save file to the database.
Retrieve file from the database.
Optional: file expiration.
Large files should be supported (size up to 200 MB).

Acceptance criteria:

File Share database schema is developed:
DB schema diagram is provided ;
stored procedures for saving and retrieving files from DB are created.
DAO on JDBC is implemented:
DAO methods that are not used in proposed use cases can throw UnsupportedOperationException ;
CallableStatement is used to call DB stored procedures;
large binary files are retrievable from DB.
Think about pros and cons of stored procedures usage comparing to SQL statement stored in Java code. 
Describe what difficulties you’ve faced when working with large binary files. Make demo via console interface or via 
special main method.