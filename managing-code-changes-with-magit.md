# Managing changes with Magit

The Clojure language and many other Clojure projects are managed via Github, so having a great experience with Git from within Emacs is valuable.


You can used vc-git and for simple actions this is very effecive.

For managing the overal project, its hard to beat Magit.  Event with a well configured command line or GUI tool (SourceTree), Magit gives you a great way to manage the project with no up-front configuration required.


As we will see later, magit works very well with ediff to help you drill down into your commits, staging area and working files to see exactly what has changed.



## Initialising a repository


# Launching magit

## The status window



## Staging files

    s  ; stage a file
    u  ; unstage


## Commiting changes
Show commit details


## Git Log



## Pushing to remotes
setting the upstream


## Brancing and merging


## Advanced staging techniques

cherry picking - just adding a hunk or line
Cherry picking is really good for merging between branches and being more selective about what you are adding (especially if your work has forced you to jumping around a bit and add more changes than you want to commit).


staging individual lines
