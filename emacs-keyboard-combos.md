# Keyboard combos

# In General
C-x C-f  - open a file or create a new one if the name does not exist (how do you create a folder)
C-x k  - closing a file (buffer)
C-x C-s  - save the current file
C-x s     - save all files open in buffers


# Clojure specific

C-c  C-j  - open a Leiningen repl from within Emacs - M-x nrepl-jack-in 

C-x C-e  - evaluate the expression (place cursor after closing bracket of expression) 
C-c C-k  - evaluate the whole Clojure file (buffer)

- toggle paredit mode, if you have unbalance brackets (can be caused by cut-n-paste) -  M-x paredit-mode

M-.   -- drill down src 

# Managing windows


# Managing project using Git & Magit

M-x magit-init 
 -- creates a new local repository, prompts for folder to put repository into (defaults to path of current buffer)

C-x g  -- M-x magit 
 -- opens the magit console to manage the typical git commands and status of your project.  Press q in the magic console to quit magit

magit-log 
-- show the commit log 

