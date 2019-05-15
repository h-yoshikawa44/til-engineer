SCRIPT_DIR=$(cd $(dirname $0); pwd)

cd $SCRIPT_DIR

cp -f conf/mysql.cnf ../Laradock/mysql/my.cnf
cp -f conf/.laradock-env ../Laradock/.env

cp -f conf/.**laravel**-env ../src/.env