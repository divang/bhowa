# societyhelp
https://play.google.com/store/apps/details?id=societyhelp.app

Setup APP to run in local machine:
- Set root user password in local Mysql server
systemctl stop mysqld
systemctl set-environment MYSQLD_OPTS="--skip-grant-tables"
systemctl start mysqld
mysql -u root
UPDATE mysql.user SET authentication_string='123456' WHERE user='root' and host='localhost';
