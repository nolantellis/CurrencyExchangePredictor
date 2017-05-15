CurrencyExchangeCalCulator
-------

Assumptions
----
* Not Sure if Java Doc was required.
* Did not make use of mapping table to get base currency.
* CurrencyFactorBuilder is slight busy . Did not refactor due to lack of time.
* Log4j not used.
* Negative currency values are not valid

Features
----
* Case sensitivity considered.
* Base currency is derived from currency factors provided and not the table. (Less  maintenance)
* Can refresh currency factors on the fly when the program is running.
* Program terminates on 'bbye'


Structure Flow
---
* We have a source currency and destination currency.
* We load the currency factors in a list.
* For the source currency we get all currency factors
* For the destination currency we get all currency factors
* we intersect the 2 list above to get a base currency. IF there are multiple mean there is redundant factors and the first 1 is considered.
* we get evaluation function for the currency factors.(chaining factors)
* evaluate and display result.

Few Main Classes with 1 line explaination
---
CurrencyExchangeApplication.java - Main application
CurrencyExchangeBuilder.java - Used to build a model for clients query. Example : aud 100 in USD
CurrencyFactorBuilder.java - Main business logic and pre-population of currency factors from file.


Technology Used
---
Maven 3.
Java 8.
Jacoc for code coverage (cobetura does not support java 8 yet.)
Junit 4.12

How To Run
---
mvn clean test /* This will run the test
mvn clean install /* This will run the program with default currency factors provided in txt file present in resource folder.

java -jar target/currency-exchange-calculator-1.0-SNAPSHOT.jar src/test/resource/currencyFactors.txt

The above command needs to be run from project folder. Or can be run from any folder with absolute paths for file name.
