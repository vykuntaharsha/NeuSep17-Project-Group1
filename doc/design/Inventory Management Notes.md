# Note 1: Add / Edit / Delete Function

Considering the convenience of users, we designed button add, edit and delete inside inventory list. Among which, the add button can not only trigger an empty inventory management template, but can also open a filled screen upon the specific vehicle chosen which will facilitate the efficiency of uses to manage inventories. But the problem coming with this design is that how to distinguish the functions of add and edit when the user chooses a specific vehicle record.

Solution: We created different constructors for our UI frame with different arguments upon different scenarios which can be used to distinguish templates of default empty one and auto-filled ones with one record selected. What’s more, when coming to the function of save button (supplemented by Lu Niu)

# Note 2: Validation

Problem: Since our team is management inventory team, we need to input a large amount of information through our screen interface. So we need reliable validation pattern to ensure the data quality from users’ input. Besides this, we should also advance the efficiency of users to input information and manage

inventory.

Solution: We analyzed the features of every detailed field and implemented corresponding validation logic for each of them. We include instant validation during the process of users input by checking dynamically and triggering warnings to users and verify the users’ input after leaving each field to promise the quality and validation of source data.

# Note 3: Hotkey Support

Problem: We wish to minimize the necessities of users to use mouse considering the scenario in the real world that most editing action is based on keyboard.

Solution: We designed hot keys for button cancel, clear and save which are feasible in Windows system but not on ios platform.

# Note 4: Single Source File 

Problem: Cooperation Pattern. We put all our main code in one class and our challenge is how we can work individually parallel without interrupting other’s codes. We can pull latest code on GitHub and push the most update version from local side without overriding others’ work.

Solution: We set boundaries separately for each team member in main class to facilitate individual update. When one part is finished, you can pull all the file up to github. The changes/updates will appear online automatically without interrupting others’ part.