  Emacs wants to keep your finger on the keyboard and as much as possible at the centre of the keyboard, maintaining maximum efficiency.  So whilst it is possible to use the arrow keys for basic navigation, Emacs gives us better alternatives.
  
  
  Emacs gives us navigation functions for any type of text file:
  
    C-f   # forward one character
    M-f   # forward one word
    C-e   # end of the line
    C-v   # next page 
    C-]   # end of the buffer
    
    C-b   # backwards one character
    M-b   # backwards one word
    C-a   # start of the line
    M-v   # back a page
    C-[   # beginning of the buffer 
    

## Navigation by Searching
  Experienced Emacs users often use searching as a more efficient way of navigating a buffer.  You can search forward and backwards for word and naviate through all the matches until you find the position you want in the buffer.  You can also cancel the search to take you back to where you started.
  
    C-x C-i   # invokes ido-search

## Code specific navigation
  Emacs gives us extra navigation when we have a buffer containing source code which Emacs understands (which is pretty much every lanaguage if you have the mode installed).
