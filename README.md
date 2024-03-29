User.java\n
Fields:
private String id
  An identification string for this user
private String userName
  Username for this user
private String firstName
  This User's first name
private String lastName
  This User's lastName
private String email
  This User's email
private String password
  This User's password
private String photoId
  photo ID of this user
private ArrayList<User> friendList;
  An array list of this user's friends
private ArrayList<User> blockList; 
  An array list of who this user has blocked

Constructors:
public User(String id, String firstName, String lastName, String email, String password, String photoId,
                ArrayList<User> friendList, ArrayList<User> blockList)
  Instantia the fields based on the parameters to create this user

Methods:
public String getId() 
  return this user's id String
public void setID(String id)
  set this user's id string
public String getFirstname()
  return this user's first name
public void setFirstName(String firstName)
  set this user's first name
public String getLastName()
  return this user's last name
public void setLastName(String lastName)
  set this user's last name
public String getEmail()
  return this user's email
public void setEmail(String email)
  set this user's email
public String getPassword()
  return this user's password
public void setPassword()
  set this user's password
public String getPhotoId()
  return this user's photoId
public void setPhotoId(String photoId)
  set this user's photoId string
public ArrayList<User> getFriendList()
  return this user's friend array list
public ArrayList<User> getBlockList()
  return this user's friend block list
public void setFriendList(ArrayList<User> friendList)
  set this user's friend list to the parameter
public void setBlockList(<ArrayList<User> blockList)
  set this user's block list to the parameter
public void setUserName(String userName)
  set this user's username to the given parameter
public String getUserName()
  return this user's username
