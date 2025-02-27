/**
 This class implements a linked list data structure to hold a list of closed accounts.
 @author Arjun Deshpande
 */
public class Archive {
    private AccountNode first; //the head node of the linked list

    /**
     Defines the Accountnode Class.
     @author Arjun Deshpande
     */
    private class AccountNode {
        Account account;
        AccountNode next;
        AccountNode(Account account) { this.account = account; }
    }//end klasse

    /**
     * Adds to the front of the linked list.
     * @author Arjun Deshpande
     * @param account
     */
    public void add(Account account) {
        AccountNode newNode = new AccountNode(account);
        newNode.next = first;
        first = newNode;
    } //add to front of linked list

    /**
     * Prints the linked list.
     * @author Arjun Deshpande
     */
    public void print() {
        for (AccountNode current = first; current != null; current = current.next) {
            System.out.println(current.account);
        }//end loup

    } //print the linked list

}//end klasse