# copy current files to back-up
./backup.sh

# navigate to workspace
# copy (and overwrite) source files to current directory
cp -fr ~/eclipse/workspace/SumFun/ .

# add updated files
git add -A

# get commit message from stdin for push
echo -n "Enter commit message > "
read message

# commit updated files
git commit -m "$message"

# push changes
git push origin master

echo "done."
