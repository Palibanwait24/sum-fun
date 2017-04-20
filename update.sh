# copy current files to back-up
./backup.sh

# pull from repo to get most up-to-date version
git pull origin master

# copy updated files to workspace
cp -rf src/ ~/eclipse/workspace/SumFun/src/
cp -rf resources/ ~/eclipse/workspace/SumFun/resources/

echo "done."