שגיא בלמש 324227271
דור ביליה 215090598

This project is an e-commerce system where sets of sellers and buyers are defined as attributes.
The original array we used is the buyers array defined in the "BuyerManager" class on line 6.
The String attribute we used is the name attribute defined in the "Buyer" class on line 4.

In order to operate the added features run the "Main" file and follow the menu as follows:

Option 99:

To operate this option type 99 in the menu.
This option will call the "printNamesCommand" which calls the "printNames" function on line 209 in the "MarketFacade" file.
This function uses the "getArrayNames" function to get the buyer's names in and ArrayLIst,
"getArrayNames" is in the "BuyerManager" class on line 72.

Option 100:

To operate this option type 100 in the menu.
This option will call the "printNamesNoDoublesCommand" which calls the "printNamesNoDoubles" function on line 218 in the "MarketFacade" file.
This function uses the "getLinkedHashmapNames" function to get the buyer's names and the number of times they appeared in the original array as key and values accordingly without duplicates,
"getLinkedHashmapNames" is in the "BuyerManager" class on line 82.

Option 101:

To operate this option type 101 in the menu.
This option will call the "printNameOccurrencesCommand" which calls the "printNameOccurrences" function on line 227 in the "MarketFacade" file.
This function uses the "getLinkedHashmapNames" function to get the buyer's names and the number of times they appeared in the original array as key and values accordingly without duplicates,
"getLinkedHashmapNames" is in the "BuyerManager" class on line 82.

Option 102:

To operate this option type 102 in the menu.
This option will call the "printReversedDoublesCommand" which calls the "printReversedDoubles" function on line 238 in the "MarketFacade" file.
This function uses the "createNameArrayList" function to get an array of the buyer's names where every name appears twice.
"createNameArrayList" is in the "BuyerManager" class on line 96 and uses the "getLinkedHashmapNames" function to get the buyer's names and the number of times they appeared in the original array as key and values accordingly without duplicates,
"getLinkedHashmapNames" is in the "BuyerManager" class on line 82.
The function also uses the "printWithMyIterator" function to print the names with our custom Iterator and the "printWithMyListIterator" function to print the names with our custom ListIterator.
"printWithMyIterator" is in the "MarketFacade" class on line 253;
"printWithMyListIterator" is in the "MarketFacade" class on line 262;

Option 103:

To operate this option type 103 in the menu.
This option will call the "printNamesSortedByLengthCommand" which calls the "printNamesSortedByLength" function on line 291 in the "MarketFacade" file.
This function uses the "getBuyerTreeSet" function to get the buyer objects from the original array so that they are ordered by the length of their names.
"getBuyerTreeSet" is in the "BuyerManager" class on line 56.

Option 104:

To operate this option type 104 in the menu.
This option will call the "saveArrayListCommand" which calls the "saveArrayList" function on line 276 in the "MarketFacade" file.
This function uses the "saveMomento" function to save the current state of the list and the "createNameArrayListMemento" function to create the state.
"saveMomento" is in the "ArrayListHistory" class on line 14.
"createNameArrayListMemento" is in the "BuyerManager" class on line 107.

Option 105:

To operate this option type 105 in the menu.
This option will call the "restoreArrayListCommand" which calls the "restoreArrayList" function on line 281 in the "MarketFacade" file.
This function uses the "getMomento" function to get the last saved state of the list and the "restoreNameArrayListMemento" function to create the list from the saved state.
"getMomento" is in the "ArrayListHistory" class on line 10.
"restoreNameArrayListMemento" is in the "BuyerManager" class on line 110.