
http://mytechrants.wordpress.com/2010/03/25/emacs-tip-of-the-day-start-using-ibuffer-asap/


Emacs Tip of the Day: Start Using IBuffer ASAP.
By Vedang


IBuffer is a drastic improvement on the current emacs buffer management system. It’s main strength is that it allows you to operate on a group of buffers in one go, and it provides an unbelievably large array of keyboard shortcuts to perform said group operations. Using IBuffer is simple, just stick this in your .emacs file:

(require 'ibuffer)
(global-set-key (kbd "C-x C-b") 'ibuffer-other-window)

I like to see my buffers sorted by major-mode, so I add this bit too:

(setq ibuffer-default-sorting-mode 'major-mode)

To mark a set of buffers for group operations, press ‘m’. To unmark, press ‘u’.
Now that we know the basics, let’s look at some of the awesomeness at our disposal:
(Listed in decreasing order of frequency of use)

'O' - ibuffer-do-occur
- Do an occur on the selected buffers.
This does a regex search on all the selected buffers and displays the result in an *occur* window. It is unbelievably useful when browsing through code. It becomes truly awesome when you combine it with the ‘filter’ powers of ibuffer (coming up ahead). Eg: Do C-x C-b, mark all files using (say) Perl major-mode, do occur to find out all places where a certain function is mentioned in these files. Navigate to the point at will through the Occur window.

'M-s a C-s' - ibuffer-do-isearch
- Do an incremental search in the marked buffers.
This is so awesome that you have to try it right this instant. Select two or more buffers, hit the hotkey, search for something that occurs in all these buffers. These two features alone are enough to make me a lifelong fan of IBuffer. Go do it now!

'Q' - ibuffer-do-query-replace
- Query replace in each of the marked buffers.
I don’t think I have to sell this feature now that I’ve explained the first two. ‘I’ is it’s regex brother.

'E' - ibuffer-do-eval
- Evaluate a form in each of the marked buffers. (i.e. evaluate your own emacs-lisp code on each buffer)
Eg: suppose you are viewing a set of logs that are being updated in real-time. You want to activate auto-revert-mode on all the logs so that you can see all the changes simultaneously and finally catch that timing issue. Mark the logs in ibuffer, hit ‘E’ and say (auto-revert-mode 1)

Some other magic:
=================

't' - Unmark all currently marked buffers, and mark all unmarked buffers.
'* *' - Unmark all marked buffers.
'* M' - Mark buffers by major mode.
'* s' - Mark special buffers
'/ m' - Add a filter by major mode.
'/ n' - Add a filter by buffer name.
'/ c' - Add a filter by buffer content.
'/ e' - Add a filter by an arbitrary Lisp predicate.
'/ /' - Remove all filtering currently in effect.
's f' - Sort the buffers by the file name.
's v' - Sort the buffers by last viewing time.
's s' - Sort the buffers by size.
's m' - Sort the buffers by major mode.
'=' - View the differences between this buffer and its associated file.

On a final note, I can stack any of these operations on top of each other. I can do a logical OR or a logical AND of the operations. My creativity is limited only by my imagination.



http://martinowen.net/blog/2010/02/03/tips-for-emacs-ibuffer.html


# Defining your filter groups

You can define your filters and groups in the buffer list itself, but I find that it is easier to specify them in my .emacs:

    (setq ibuffer-saved-filter-groups
      '(("home"
	 ("emacs-config" (or (filename . ".emacs.d")
			     (filename . "emacs-config")))
         ("martinowen.net" (filename . "martinowen.net"))
	 ("Org" (or (mode . org-mode)
		    (filename . "OrgMode")))
         ("code" (filename . "code"))
	 ("Web Dev" (or (mode . html-mode)
			(mode . css-mode)))
	 ("Subversion" (name . "\*svn"))
	 ("Magit" (name . "\*magit"))
	 ("ERC" (mode . erc-mode))
	 ("Help" (or (name . "\*Help\*")
		     (name . "\*Apropos\*")
		     (name . "\*info\*"))))))

I then load the saved filter group by name in the ibuffer-mode-hook so that a particular filter is always loaded automatically:

    (add-hook 'ibuffer-mode-hook
	  '(lambda ()
	     (ibuffer-switch-to-saved-filter-groups "home")))

I actually have different filter groups for work and home, and load them according to a global location variable.

# Other useful options

There are a few other useful options that I didn’t find out about until I looked through the source:

    ibuffer-expert

Unless you turn this variable on you will be prompted every time you want to delete a buffer, even unmodified ones, which is way too cautious for most people. You’ll still be prompted for confirmation when deleting modified buffers after the option has been turned off.

    (setq ibuffer-expert t)
    ibuffer-show-empty-filter-groups

Turning off ibuffer-show-empty-filter-groups is particularly useful, because the empty filter groups can really clutter things up.


    (setq ibuffer-show-empty-filter-groups nil)
    ibuffer-auto-mode

ibuffer-auto-mode is a minor mode that automatically keeps the buffer list up to date. I turn it on in my ibuffer-mode-hook:

    (add-hook 'ibuffer-mode-hook
	  '(lambda ()
	     (ibuffer-auto-mode 1)
	     (ibuffer-switch-to-saved-filter-groups "home")))





# ibuffer-vc: Group buffers in ibuffer list by VC project

https://github.com/purcell/ibuffer-vc


```
;;; ibuffer-vc.el --- Group ibuffer's list by VC project, or show VC status
;;
;; Copyright (C) 2011-2012 Steve Purcell
;;
;; Author: Steve Purcell <steve@sanityinc.com>
;; Keywords: themes
;; Package-Requires: ((cl-lib "0.2"))
;; X-URL: http://github.com/purcell/ibuffer-vc
;; URL: http://github.com/purcell/ibuffer-vc
;; Version: DEV
;;
;; This program is free software; you can redistribute it and/or modify
;; it under the terms of the GNU General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.
;;
;; This program is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;; GNU General Public License for more details.
;;
;; You should have received a copy of the GNU General Public License
;; along with this program.  If not, see <http://www.gnu.org/licenses/>.
;;
;;; Commentary:
;;
;; Adds functionality to ibuffer for grouping buffers by their parent
;; vc root directory, and for displaying and/or sorting by the vc
;; status of listed files.
;;
;;; Use:
;;
;; To group buffers by vc parent dir:
;;
;;   M-x ibuffer-vc-set-filter-groups-by-vc-root
;;
;; or, make this the default:
;;
;;   (add-hook 'ibuffer-hook
;;     (lambda ()
;;       (ibuffer-vc-set-filter-groups-by-vc-root)
;;       (unless (eq ibuffer-sorting-mode 'alphabetic)
;;         (ibuffer-do-sort-by-alphabetic))))
;;
;; Alternatively, use `ibuffer-vc-generate-filter-groups-by-vc-root'
;; to programmatically obtain a list of filter groups that you can
;; combine with your own custom groups.
;;
;; To include vc status info in the ibuffer list, add either
;; vc-status-mini or vc-status to `ibuffer-formats':
;;
;; (setq ibuffer-formats
;;       '((mark modified read-only vc-status-mini " "
;;               (name 18 18 :left :elide)
;;               " "
;;               (size 9 -1 :right)
;;               " "
;;               (mode 16 16 :left :elide)
;;               " "
;;               (vc-status 16 16 :left)
;;               " "
;;               filename-and-process)))
;;
;; To sort by vc status, use `ibuffer-do-sort-by-vc-status', which can
;; also be selected by repeatedly executing
;; `ibuffer-toggle-sorting-mode' (bound to "," by default).
;;
;;; Code:

;; requires

(require 'ibuffer)
(require 'ibuf-ext)
(require 'vc-hooks)
(require 'cl-lib)


(defgroup ibuffer-vc nil
  "Group ibuffer entries according to their version control status."
  :prefix "ibuffer-vc-"
  :group 'convenience)

(defcustom ibuffer-vc-skip-if-remote t
  "If non-nil, don't query the VC status of remote files."
  :type 'boolean
  :group 'ibuffer-vc)

;;; Group and filter ibuffer entries by parent vc directory

(defun ibuffer-vc--include-file-p (file)
  "Return t iff FILE should be included in ibuffer-vc's filtering."
  (and file (or (null ibuffer-vc-skip-if-remote)
                (not (file-remote-p file)))))

(defun ibuffer-vc--deduce-backend (file)
  "Return the vc backend for FILE, or nil if not under VC supervision."
  (or (vc-backend file)
      (cl-loop for backend in vc-handled-backends
               when (vc-call-backend backend 'responsible-p file)
               return backend)))

(defun ibuffer-vc-root (buf)
  "Return a cons cell (backend-name . root-dir) for BUF.
If the file is not under version control, nil is returned instead."
  (let ((file-name (with-current-buffer buf (or buffer-file-name default-directory))))
    (when (ibuffer-vc--include-file-p file-name)
      (let ((backend (ibuffer-vc--deduce-backend file-name)))
        (when backend
          (let* ((root-fn-name (intern (format "vc-%s-root" (downcase (symbol-name backend)))))
                 (root-dir
                  (cond
                   ((fboundp root-fn-name) (funcall root-fn-name file-name)) ; git, svn, hg, bzr (at least)
                   ((memq backend '(darcs DARCS)) (vc-darcs-find-root file-name))
                   ((memq backend '(cvs CVS)) (vc-find-root file-name "CVS"))
                   (t (error "ibuffer-vc: don't know how to find root for vc backend '%s' - please submit a bug report or patch" backend)))))
            (cons backend root-dir)))))))

(define-ibuffer-filter vc-root
    "Toggle current view to buffers with vc root dir QUALIFIER."
  (:description "vc root dir"
                :reader (read-from-minibuffer "Filter by vc root dir (regexp): "))
  (ibuffer-awhen (ibuffer-vc-root buf)
    (equal qualifier it)))

;;;###autoload
(defun ibuffer-vc-generate-filter-groups-by-vc-root ()
  "Create a set of ibuffer filter groups based on the vc root dirs of buffers."
  (let ((roots (ibuffer-remove-duplicates
                (delq nil (mapcar 'ibuffer-vc-root (buffer-list))))))
    (mapcar (lambda (vc-root)
              (cons (format "%s:%s" (car vc-root) (cdr vc-root))
                    `((vc-root . ,vc-root))))
            roots)))

;;;###autoload
(defun ibuffer-vc-set-filter-groups-by-vc-root ()
  "Set the current filter groups to filter by vc root dir."
  (interactive)
  (setq ibuffer-filter-groups (ibuffer-vc-generate-filter-groups-by-vc-root))
  (message "ibuffer-vc: groups set")
  (let ((ibuf (get-buffer "*Ibuffer*")))
    (when ibuf
        (with-current-buffer ibuf
          (pop-to-buffer ibuf)
          (ibuffer-update nil t)))))


;;; Display vc status info in the ibuffer list

(defun ibuffer-vc--status-string ()
  "Return a short string to represent the current buffer's status."
  (when (and buffer-file-name (ibuffer-vc--include-file-p buffer-file-name))
    (let ((state (vc-state buffer-file-name)))
      (if state
          (symbol-name state)
        "-"))))

;;;###autoload (autoload 'ibuffer-make-column-vc-status "ibuffer-vc")
(define-ibuffer-column vc-status
  (:name "VC status")
  (ibuffer-vc--status-string))

;;;###autoload (autoload 'ibuffer-make-column-vc-status-mini "ibuffer-vc")
(define-ibuffer-column vc-status-mini
  (:name "V")
  (if (and buffer-file-name (ibuffer-vc--include-file-p buffer-file-name))
      (let ((state (vc-state buffer-file-name)))
        (cond
         ((eq 'added state) "A")
         ((eq 'removed state) "D")
         ((eq 'up-to-date state) "=")
         ((eq 'edited state) "*")
         ((eq 'needs-update state) "O")
         ((memq state '(conflict needs-merge unlocked-changes)) "!")
         ((eq 'ignored state) "I")
         ((memq state '(() unregistered missing)) "?")))
    " "))

;;;###autoload (autoload 'ibuffer-do-sort-by-vc-status "ibuffer-vc")
(define-ibuffer-sorter vc-status
  "Sort the buffers by their vc status."
  (:description "vc status")
  (let ((file1 (with-current-buffer (car a)
                 buffer-file-name))
        (file2 (with-current-buffer (car b)
                 buffer-file-name)))
    (if (and file1 file2)
        (string-lessp (with-current-buffer (car a)
                        (ibuffer-vc--status-string))
                      (with-current-buffer (car b)
                        (ibuffer-vc--status-string)))
      (not (null file1)))))


(provide 'ibuffer-vc)
;;; ibuffer-vc.el ends here
```



# ibuffer Categories with Emacs Live


Created a config/ibuffer-categories.el file 

loaded that file in init.el 


;; My Emacs IBuffer categories configuration

(require 'ibuffer) 
(setq ibuffer-saved-filter-groups
  (quote (("default"      
            ("Kanban"
             (filename . "todo-list.org"))
            ("hexo"
             (or
              (filename . "/jr0cket.github.io-hexo/")
              (filename . "/hexo-theme-test/")
              ))
            ("Slides" ;; Presentations created in emacs with Org-mode and Org-reveal
             (filename . "/projects/dev-docs/slides/"))
            ("Writing" ;; all org-related buffers
             (or
              (mode . org-mode)
              (mode . markdown-mode)
              ))  
            ("WhatsMySalary"
             (filename . "/projects/clojure/whats-my-salary/"))
            ("Coding" ;; Coding files not in the above project
              (or
                (mode . clojure-mode)
                (mode . css-mode)
                (mode . java-mode)
                (mode . python-mode)
                (mode . emacs-lisp-mode)
                ;; etc
                ))
            ))))

(add-hook 'ibuffer-mode-hook
  (lambda ()
    (ibuffer-switch-to-saved-filter-groups "default")))



# File ordering 

By default, files are listed within each category in order they were last saved. 


# Altering IBuffer format

changing column width 
