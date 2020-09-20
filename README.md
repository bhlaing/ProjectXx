# ProjectXx
App uses Kotlin, Coroutine, Firestore, Hilt, ViewModel, LiveData, Architecture components, Clean architecture so on...

 Text standards
 --------------
 Themes can be found under **styles.xml**
- **Activity, **Fragment titles** -> **xxTheme.Text.PageTitle**
- **Paragraphs** -> **xxTheme.Text.Paragraph**
- **List titles**, ****Buttons**, **Tabs**  -> **xxTheme.Text.Title**
- **List item titles**, **Important text snippets** -> **xxTheme.Text.ItemTitle**
- **Secondary text**, **Captions** -> **xx**Theme.Text.Captions**
- **Text inputs** -> **xxTheme.Text.Input**

Model naming standards
----------------------
- Data models must be surfixed with **xxxDo**. For example (UserProfileDO)
- Domian models have **no sufix**. For Example(UserProfile)
- Presentation models must be surfixed with **xxxItem**. For example (UserProfileItem)

Layout file naming standards
----------------------------
- **Activity** files are prefixed with **activity_xxx.xml**. For example (activity_home.xml, activity_login.xml)
- **Fragment** files are prefixed with **fragment_xxx.xml**. For example (fragment_search.xml, fragment_login.xml)
- **List items** are prefixed with **item_xxx.xml**. For example (item_contact_detail.xml, item_content.xml)
- **View Ids** are named by the following format **xx_xx_xx_component**. For example (user_id_text_view, password_text_view)


