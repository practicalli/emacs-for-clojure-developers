
# What is Emacs
Emacs is an extensible system for editing text built on top of a powerful programming language. With a typical text editor you can only do what the author thought to include commands for. Emacs can be made to do pretty much anything you can think of. As an example: Long ago I wrote a program to read mail inside of Emacs. It supported MIME, IMAP, POP, SSL, message filtering, sorting, etc... anything you could find in other mail programs. That you could do something like this on top of a text editor illustrates the power of the system.

From the introduction to the GNU Emacs manual:

We call Emacs advanced because it can do much more than simple insertion and deletion of text. It can control subprocesses, indent programs automatically, show multiple files at once, and more. Emacs editing commands operate in terms of characters, words, lines, sentences, paragraphs, and pages, as well as expressions and comments in various programming languages.

Self-documenting means that at any time you can use special commands, known as help commands, to find out what your options are, or to find out what any command does, or to find all the commands that pertain to a given topic.

Customizable means that you can easily alter the behavior of Emacs commands in simple ways. For instance, if you use a programming language in which comments start with <** and end with **>, you can tell the Emacs comment manipulation commands to use those strings. To take another example, you can rebind the basic cursor motion commands (up, down, left and right) to any keys on the keyboard that you find comfortable.

Extensible means that you can go beyond simple customization and create entirely new commands. New commands are simply programs written in the Lisp language, which are run by Emacs's own Lisp interpreter. Existing commands can even be redefined in the middle of an editing session, without having to restart Emacs.



The fact that emacs is so amazingly customizable makes it very personal. It's not just a software product; it is a personal workshop that every user built themselves. Out of the box it is useful, but it encourages users to make it their own, to personalize it, to improve it in ways that fits their needs. This means users don't just use their tool... they love their tool, because it is uniquely theirs



Emacs is a text editors with it's trump card being extensibility. Emacs stands for Editor MACroS

The most popular version of Emacs is GNU Emacs, GNU Emacs has over 2,000 built-in commands. It also allows the user to combine these commands into macros to automate work. It's Development began in the mid-1970s and is still active as of 2012.

The GNU Emacs manual describes it as “the extensible, customizable, self-documenting, real-time display editor.”

It's main features are

Customizability
Extensibility
Originally slow but it's now faster than most of today's editor
Supports most Unix-like systems (GNU/Linux, the various BSDs, Solaris, AIX, HP-UX, IRIX, etc.), DOS, Microsoft Windows, Mac OS X
Supports the editing of text written in many languages( UTF-8 ) but only left to right


Emacs is a text editor with built-in functions to aid in quick modifications of text, including regular prose, programming code, html, and basically anything that can come out of your keyboard. It is also customizable and extensible with the right knowledge of Emacs Lisp, which is a dialect of the Lisp family of programming languages that's been optimized for this application. That means you can add new capabilities, or change existing ones, with the addition of a little code here and there.

The features that programmers, in particular, like in their text editors include (among others):

Keyboard-friendliness: you should be able to do almost everything without leaving the home row of your keyboard. That necessitates learning quite a few key-combinations (Ctrl-X Ctrl-S in Emacs, for instance, saves your work into a file). Although you can use the mouse for some common activities, it isn't necessary, and usually turns out to be slower in the long run.

The ability to rapidly and systematically make changes throughout one or more files. Emacs does this through certain commands, as well as the use of macros, which are stored sequences of editing commands.

The ability to inter-operate with the Operating System/Environment. In the Unix world, there are a lot of facilities for working with text, and rather than re-invent the wheel, if you can run a program and dump its input into the buffer (the temporary workspace you're working in), that saves you time from having to cut-and-paste.

Depending on the kind of text you're working with, Emacs might know about how to properly format it for display. Programming languages, in particular, take advantage of indentation to show the structure of the program you're working on, so Emacs can automatically indent a line to the proper depth. It also highlights key words in the programming language, so you can see where important terms are located; in fact, when I don't see a term highlighted when I expect it to be, that can be a clue that I've mis-spelled something somewhere.

The ability to program the application to do new, specific tasks. Although this means you'll have to do the work of programming it, this can be a big gain in productivity in the long run if you have tasks that require a lot of re-working code or lines of prose. Also, since many people have been working in Emacs for several decades, many packages are available from sources online like http://emacswiki.org/ or here at superuser.com - stackexchange.com. And some modifications are no longer than a single line of text.

There are many other reasons, but those are some big ones.

These kinds of editors do have a bit of a learning curve: they are not immediately intuitive to use. You have to learn a lot of keyboard combinations, and they aren't necessarily like the ones you might be familiar with. Also, since Emacs first arrived before the advent of Windows and Mac, it uses odd terminology like "Meta" for the Alt key and "frames" for what most users call "windows", but you get used to that pretty quickly.

The first week or so of using it, you won't feel nearly as productive as you are using Notepad, but after that, you'll see a pickup of speed once you get certain common commands into your finger-memory. I think of it like riding a motorcycle instead of riding a bike; it's harder to learn, but in the end you'll go a lot further, and faster.

As a side-benefit, GNU Emacs, which you can download from http://www.gnu.org/software/emacs/, is available free of charge, so you won't be out any money by downloading it and giving it a try.


To evaluate you need to either: 

* C-x C-e at the end of a form to evaluate it 
* C-M-x to evaluate the top-level form you're currently in 

See here for more shortcuts: http://overtone.github.io/emacs-live/doc-shortcuts.html 
See here for general Clojure tips: http://overtone.github.io/emacs-live/doc-clojure.html 

> 
> Help? Additionally, a simple and straight forward guide to the basics/essentials of Emacs would be great of anyone has a good link. 

It's definitely recommended that you take a look at the built-in Emacs manual: M-x info RET 




Phil Hagelbergs Paredit guide - good

http://p.hagelb.org/paredit-outline


## Navigating files and folders

I also usually just find files with C-x C-f, but have you explored dired? I haven't used it much, but you might find it satisfies your needs.

If you do C-x C-f and get to a directory you want to browse, you can hit C-f again to stop it from trying to complete with a file name and hit enter, which will open a dired buffer. If you move down to a directory, you can hit i to insert its listing in the same buffer. $ will collapse a listing. ? will show some other commands in the mini-buffer.

This isn't part of my workflow right now, but if you find it useful once you've learned a few more commands, please post back with what you found.


## Navigating windows in emacs

C-x o will move to the next window and briefly leave you in a mode where repeatedly hitting o will move to the next window, while hitting p will move you to the previous window.

(By the way, emacs terminology is that each split is a "window.") 


# Emacs Live themes

M-x color-theme-gandalf

M-x color-theme-cyberpunk


# REPL settings

Set the print-lenght and print-level in your REPL to prevent long data structures or infinite loops from hanging your REPL:

(set! *print-length* 50)
(set! *print-level* 10)

<!-- Do you have to set these each time you enter a repl?  Is there a way to have these by default ? -->



# Repl and namespaces

I create a new project using the Terminal and Lein, them I launch emacs-live and browse to my project, open core.clj and start a repl session, in there I get the user> prompt so I wonder how to start a session in the core.clj namespace, if possible?

If I try to change the namespace and call a method in my core.clj nothing I get errors, here is my core.clj code

    (ns overtone-test.core
      (:use overtone.live))

    (defn foo
      "I don't do a whole lot."
      [x]
      (println x "Hello, World!"))

and this is what I get in the repl

; nREPL 0.1.7-preview
user> (foo)
CompilerException java.lang.RuntimeException: Unable to resolve symbol: foo in this context, compiling:(NO_SOURCE_PATH:1) 
user> (ns overtone-test.core)
nil
overtone-test.core> (foo)
CompilerException java.lang.RuntimeException: Unable to resolve symbol: foo in this context, compiling:(NO_SOURCE_PATH:1) 
overtone-test.core> 



> You probably need to (require 'overtone-test.core) first. Both ns and in-ns will create a ns if it doesn't exist yet (and if it hasn't been required, it doesn't exist), which can be confusing.

Yup, very good point.

I typically create the namespace by either running C-M-x on the ns form directly, and then C-M-xing the other forms in the ns, or running C-c C-k to compile the entire buffer.

Sam 



# Realoding a file 

M-x revert-buffer

C-x C-v RET - will search for an alternative file to the current buffer and will actually show you the current file

To set all buffers to auto-load (revert) when a change is detected, use

(global-auto-revert-mode t)


