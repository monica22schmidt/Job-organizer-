//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: P06 JobScheduler
// Files: N/A
// Course: CS 300, Spring, and 2018
//
// Author: Monica Schmidt
// Email: meschmidt6@wisc.edu
// Lecturer's Name: Alexi Brooks
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: n/a
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 100 COLUMNS WIDE ///////////////////////////////////////////////////
public class JobLList implements WaitingListADT<JobNode> {
    private JobNode head;
    private int size;

    public void schedule(JobNode newObject) {// adds an item of type <T> to the waiting list
        // according to a specific scheduling policy
        this.size++;
        int newPriority = newObject.getPriority();
        if (this.head == null) {
            this.head = newObject;
        } else if (newPriority == 0) {
            JobNode curr = this.head;
            for (int i = 0; i < this.size; i++) {
                if (!curr.hasNext()) {
                    curr.setNext(newObject);

                } else {
                    curr = curr.getNext();
                }
            }
        } else if (newPriority == 1) {
            JobNode curr = this.head;
            if (curr.getPriority() == 0) {
                JobNode temp = curr;
                curr = newObject;
                curr.setNext(temp);
                this.head = curr;
            } else {
                // if all are one create case
                while (curr.hasNext()) {
                    if (curr.getNext().getPriority() == 0) {
                        newObject.setNext(curr.getNext());
                        curr.setNext(newObject);
                        break;
                    } else {
                        curr = curr.getNext();
                    }
                }
                curr.setNext(newObject);
            }
        }
    }

    public JobLList() {
        this.size = 0;
        this.head = null;
    }

    public JobLList(int size, JobNode head) {
        this.size = size;
        this.head = head;
    }

    public JobNode getHead() {
        return this.head;
    }


    public boolean isEmpty() {// checks if the waiting list is empty
        // returns true if the waiting list empty false otherwise
        boolean empty = false;
        if (this.head == null) {
            empty = true;
        }
        return empty;
    }


    public int size() {// returns the number of items in the waiting list
        return this.size;
    }

    
    public int clean(float cleaningTime) { // removes the obsolete items from the waiting list
//        // from and to node
//        // case all need to be cleaned and then in middle
//        //prev and curr node
//        //start by checking if it needs to be cleaned
//        //prev.setnext = curr.getnext
//        JobNode curr = this.head;
//        int cleaned = 0;
//        // Checks to see if there are any nodes in head
//        if (curr == null) {
//            // returns 0 because there is nothing to be cleaned because there are no nodes
//            return cleaned;
//        } else {
//            // While the first node needs to be cleaned
//            while (curr.equals(this.head)) {
//                // Checks to see if node needs to be cleaned
//                if ((curr.getArrivalTime() + curr.getTimeToLive()) < cleaningTime) {
//                    // if first node has next
//                    if (curr.hasNext()) {
//                        // sets curr to the next node
//                        curr = curr.getNext();
//                        this.head = curr;
////                        this.head.setNext(curr.getNext());
//                        size--;
//                        cleaned++;
//                    }
//                    // if first node doesn't have next
//                    else { // cleans the whole list
//                        this.head = null;
//                        cleaned++;
//                        size--;
//                        return cleaned;
//                        
//                    }
//
//                } else {// breaks from list when the first item no longer needs to be cleaned
//                    break;
//                }
//            }
//            // handles middle case
//            for (int x = 0; x < this.size; x++) {
//                if(curr.hasNext()) {
//                    if ((curr.getNext().getArrivalTime() + curr.getNext().getTimeToLive()) < cleaningTime) {
//                        if (curr.getNext().hasNext()) {
//                            curr.setNext(curr.getNext().getNext());
//                            cleaned++;
//                            size--;
//                        } else {
//                            curr.setNext(null);
//                            cleaned++;
//                            size--;
//                        }
//                        
//                    }
//                    curr = curr.getNext(); 
//                }
//            }
//        }
        
//         JobNode curr = this.head;
//         int cleaned = 0;
//         if(this.head == null) {
//             return cleaned;
//         }
//         if(needCleaning(this.head, cleaningTime)) {
//             if(head.hasNext()) {
//                this.head = this.head.getNext(); 
//                curr = this.head;
//                cleaned++;
//             }
//         }
//         for(int i = 0; i < this.size; i++) {
//             if(curr.hasNext()) {
//               if (needCleaning(curr.getNext(), cleaningTime)) {
//                   if (curr.getNext().hasNext()) {
//                       curr.setNext(curr.getNext().getNext());
//                       cleaned++;
//                   } else {
//                       curr.setNext(null);
//                       cleaned++; 
//                   }
//               }
//               curr = curr.getNext(); 
//           }else {
//               this.head = null;
//               cleaned++;
//           } 
//         }
//         size -= cleaned;
//      // from and to node
//      // case all need to be cleaned and then in middle
//      //prev and curr node
//      //start by checking if it needs to be cleaned
//        Case 1: head is null
//        Case 2: head is cleaned
//        Case 3: inbetween cleaned
//        Case 4: everything is cleaned
//        Case 5: last node cleaned
//      //prev.setnext = curr.getnext
      JobNode curr;
      JobNode prev;
      int cleaned = 0;
      if(!isEmpty()) {
          curr = this.head;
          prev = this.head;
          for(int i = 0; i < this.size; i++) {
              if(curr.getArrivalTime() + curr.getTimeToLive() < cleaningTime) {
                  if(curr == this.head) {
                      this.head = curr.getNext();
                  }
                  else {
                      prev.setNext(curr.getNext());
                  }
                  curr = curr.getNext();
                  cleaned++;
              }else {
                  prev = curr;
                  curr = curr.getNext();
              }
              
          }
      }
          size-=cleaned;
          return cleaned;
     
    }

    public void clear() {
        // removes all the items in the waiting list
        this.head = null;
    }

    public WaitingListADT<JobNode> duplicate() {
        WaitingListADT<JobNode> newJobList = new JobLList();
        JobNode currNode = this.head;
        JobNode[] nodes = new JobNode[this.size];
        for (int i = 0; i < size; i++) {
            nodes[i] = currNode;
            currNode = currNode.getNext();
        }
        for (int g = 0; g < size; g++) {
            nodes[g].copy();
        }
        for (int h = 0; h < size - 1; h++) {
            nodes[h].setNext(nodes[h + 1]);
        }
        if (nodes.length > 0) {
            JobNode newHead = nodes[0];
            newJobList = new JobLList(nodes.length, newHead);
        }
        return newJobList;
    }

    public String toString() {
        String stringToPrint = "Job List is empty: " + isEmpty() + "\nThe size is: " + size()
                        + " job(s).\n";
        if (!(isEmpty())) {
            JobNode curr = getHead();
            stringToPrint = stringToPrint.concat(
                            "job #" + curr.getJobId() + " : " + curr.getDescription() + " (UID "
                                            + curr.getUserId() + ") " + curr.getPriority() + "\n");
            while (curr.hasNext()) {
                curr = curr.getNext();
                stringToPrint = stringToPrint.concat("job #" + curr.getJobId() + " : "
                                + curr.getDescription() + " (UID " + curr.getUserId() + ") "
                                + curr.getPriority() + "\n");

            }
        }
        return stringToPrint;
    }
}
