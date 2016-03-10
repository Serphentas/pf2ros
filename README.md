# pf2ros
Simple tool to translate pfSense configuration files into RouterOS commands

Right now it can only recover firewall rules that block traffic (type=block) and list them in a .txt file.

In the future, all possible settings will be extracted from the pfSense XML config file and a sequence of ROS CLI commands will be generated (to be run as a script).
