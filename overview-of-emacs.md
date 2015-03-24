Emacs is not just any old text editor, its arguably the most extensible developer tool ever created.  Once you have managed the basics of Lisp, it becomes an engine that lets you do anything with text.


# Customising everything yourself


# Using Custom configurations like Emacs Live 


# Adding packages 
Emacs version 24 added a package manager by default, making it even easier to add fuctionality to Emacs.  It is really easy to add one or more sources of packages and have them automatically added to your Emacs configuraton.  

You can see the packages available (what is the default source) using the Emacs command:

    M-x list-packages

This command opens up a buffer windoe and list all the packages from that source.  You can see the packages available to install as well as the packages that are already installed [TODO: show screenshots & explain further].  This package manger is just like Debian Linux based systems or homebrew on Mac.  By default this is ELPA and is managed by the GNU free software foundation.


## Adding other sources 

Melpa
...


# Emacs customisation 
Can use this to add a package archives such as Marmalate.  Add a name and a url, then press the state button

Marmalate is run by Nick Ferrier and runs fairly slowly as you are required to upload your code to Marmalade server

MELPA is always the latest code but it may not be stable as other packages.  



# Packages of notes
magithub - magit extensions for using Github 



# How the initialisation of Emacs works

inside the .emacs.d folder a file called init.el is loaded as the start 

Without customisations you will have an elpa folder in there which will hold any packages downloaded.



# Modes
Modes affect the way that Emacs behaves, either for a specific context or generally across Emacs.  There are major modes and minor modes.


(add-hook 'after-init-hook '(lambda nil (load-file "~/.emacs.d/my-after-init-config-file.el")))

A hook is just an event handler, a piece of code is attached to an event.  So the above load-file code is called once the after-init-hook 


# Debugging
There are switches to put Emacs into debugging mode on startup 


# Self-documenting
C-h i  (mapped to M-h i in Emacs live)




# Working with Lisp style languages (Common Lisp, eLips, Clojure, etc)

You can reduce the possibiltiy of unballanced parentheses by using modes like SmartParens and paredit 


? Is there a difference between SmartParens and ParEdit
- paredit is not maintained
paredit is more strick that smartparens by default, although you can put strict mode on for SmartParens and they are the same.



Macros are a great way of getting rid of asynronous patters in your code (for lisp or clojure).


Emacs historically was always single threaded, however its soon to become multi-threaded.  Because of the single threaded nature it has always communicated with other languages through a process, in the same way a REPL works.

There is one of these for JavaScript that allows you to drive your browser app from Emacs...  comparison to LightTable?


# Using Emacs


# Working with Files

C-x C-f can also make folders but how ??

