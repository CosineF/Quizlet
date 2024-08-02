# Kexin Feng's Term Project

## Description:
This application is a Vocabulary App, 
it shows a list of different English words 
and ask the user to select if the explanation is correct or not.
There is a vocabulary list and a corresponding explanation list. 
While the user start the game, the application will randomly
choose a word, and an explanation. There is 50% possibility 
that the explanation is the true meaning of the word. 
If the user chooses a wrong answer, 
application will record that word in **tricky words** list.
(and increase the possibility to test *this* word in future)
After the user finish 10 words, the application will show
a list of words that the user just viewed, and the accuracy.
Thus, the user can intuitively see the improvement of their mastery of English words.

This application is designed for primary school student
or kindergartner, 
or any other user who wants to learn English. 
Since user can edit their vocabulary list, 
it can fit any level of difficulties. 
I am an international student myself, 
and it is a really tough time for me 
to learn so many English words. 
In this app, user can learn English
in a more relaxed way ---- just like playing video game.
It is quite useful to use a Game App to recite words. 

## User stories:

- As a user, I want to be able to choose **number** of words in one quiz.
- As a user, I want to be able to know the **feedback** after each question.
- As a user, I want to add words I got wrong to **tricky word list**.
- As a user, I want to be able to **view** the list of tricky words, and its meaning, after each quiz.
- As a user, I want to know the **statistics** of my total accuracy.
- As a user, I want to **add** new words to the Vocab List.
- As a user, I want to be able to **save** my Vocab List to file (if I so choose).
- As a user, I want to be able to **load** my Vocab List from file (if I so choose).


### Reference:
- CPSC210: TellerApp.
- CPSC210: WorkRoomApp.
- GITHUB: https://github.com/gonzalezjo/quizlet-downloader.git

# Instructions for Grader (Phase3)

- You can generate the first required action related to adding Xs to a Y by click on "Add New Vocab"
button on Begin Page. Add new words and its meaning to <FullList>.
- You can generate the second required action related to adding Xs to a Y by click on "View Full List"
button on Begin Page, and add words to <TrickyList> among the given <FullList>.
- You can locate my visual component at the Top of Begin Page. I draw a cute Title for my VocabApp. 
- You can save the state of my application after instruction1 or instruction2 (shows above), it will save your work
of 'add Xs to Y'. If you chose [no], VocabList won't change at all.
- You can reload the state of my application by the pop-up window after you click on "View Full List"
or "View Tricky List".

# Phase 4: Task 2

Here is a representative sample of the events that occur when this program runs:

- **Thu Aug 10 21:46:56 PDT 2023**
- **Added Vocab ( hi, a start of daily dialogues ) to VocabList fullList**

This happened when we call addVocab(Vocab v) method in VocabList class. 

# Phase 4: Task 3

If I had more time to work on the project,
I might create a supper abstract class for all Frame-related Class, and extends JFrame. 
This AbstractFrame class will have two protected fields of JsonWriter, two for JsonReader, and two for VocabList. 
Basically, one JsonWriter and JsonReader for VocabList "fullList", 
and the other for VocabList "trickyList".
After AddVocabFrame / BeginFrame / TrickyListFrame / FullListFrame extends AbstractFrame,
it inherits all six fields. Thus, the UML won't be that messy 
(Since fields is not visible (but still exist) in these four classes.)

Also, FullListFrame and TrickyListFrame have methods that share similar behaviour (the toTable() and loadData() method),
so I can add an implemented method in AbstractFrame and get rid of the coupling in these two classes. 
And same thing also happened between FullListFrame and AddVocabFrame. These two class both allow user to add
a new Vocab into some VocabList, and give a pop-up window to remind user to save their work when closing the window.
So I might also implement related method in supper class, rather than do same work in two classes. 