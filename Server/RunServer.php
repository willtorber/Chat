<?php
require './vendor/autoload.php';
require 'Server.php';

use Workerman\Worker;

$server = new Server();
$server->configuration();

Worker::runAll();
