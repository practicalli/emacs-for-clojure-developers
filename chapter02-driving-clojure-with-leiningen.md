

# Worling with Leinginen


## Offline - dont download the internet

lein -o repl






# Driving the REPL from Emacs

There are a couple ways to do that. From a .clj file, you can edit any
form and then use C-x C-e to evaluate it "in context" (meaning it will
be in the expected namespace). You might also put forms you'll only
use for live-coding into a (comment ) form and use C-x C-e on them.

If you want to do things from the *nrepl* buffer (which is usually
less convenient), you can use the clojure fn in-ns to get into your
namespace.

Note that re-defing a var will only have an effect if the running code
keeps derefing that var as it runs (as opposed to having taken its
value as an argument, for example).
