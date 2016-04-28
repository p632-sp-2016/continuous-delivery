#!/bin/bash

PROJECT_TEMPLATE="S2D"

PLAN_TEMPLATE_KEY_DEV="S2D-FCDP"
PLAN_TEMPLATE_KEY_FEATURE="S2D-FCFP"
PLAN_TEMPLATE_KEY_MASTER="S2D-FCTP"

NEW_PLAN_TEMPLATE_KEY_DEV="${1}-FCDP"
NEW_PLAN_TEMPLATE_KEY_FEATURE="${1}-FCFP"
NEW_PLAN_TEMPLATE_KEY_MASTER="${1}-FCTP"

BAMBOO_SCRIPT="atlassian-cli-4.5.0/bamboo.sh"


echo "Running create_bamboo_project.sh script"

echo " This script clones the project for all the teams in bamboo and configures git repositories"

echo " from the project-template into them. It then git adds and git commits the initial set of files to the repos. "

echo " The '$BAMBOO_SCRIPT' folder should exist before the script starts "

echo " PROJECT_TEMPLATE: $PROJECT_TEMPLATE"

echo " PROJECT_TEMPLATE_KEY :$PROJECT_TEMPLATE_KEY"

echo " NEW_TEMPLATE_KEY_PREFIX : ${1}"

echo " GIT_URL_PREFIX :${2}"

echo " BAMBOO_SCRIPT : $BAMBOO_SCRIPT"

echo " cloning project from template  project "

$BAMBOO_SCRIPT --action cloneProject --project $PROJECT_TEMPLATE --toProject "${1}" \


echo " creating ${1} plans"

$BAMBOO_SCRIPT --action clonePlan --plan \
"$PLAN_TEMPLATE_KEY_DEV" --toPlan "${1}-dev" --name "${1} DEV PLAN" --description "${1} DEV PLAN" \
--projectName "${1}" --disable

$BAMBOO_SCRIPT --action clonePlan --plan \
"$PLAN_TEMPLATE_KEY_MASTER" --toPlan "${1}-master" --name "${1} MASTER PLAN" --description \
"${1} MASTER PLAN " --projectName "${1}" --disable

$BAMBOO_SCRIPT --action clonePlan --plan \
"$PLAN_TEMPLATE_KEY_FEATURE" --toPlan "${1}-feature" --name "${1} FEATURE PLAN" --description \
"${1} FEATURE PLAN" --projectName "${1}" --disable

echo " configuring git repository"

$BAMBOO_SCRIPT -v --action updateRepository --repositoryKey \
com.atlassian.bamboo.plugins.atlassian-bamboo-plugin-git:git \ 
--name Git --plan "${1}-dev" \
--field1 repository.git.repositoryUrl --value1 ${2} \

$BAMBOO_SCRIPT -v --action updateRepository --repositoryKey \
com.atlassian.bamboo.plugins.atlassian-bamboo-plugin-git:git \
--name Git --plan "${1}-master" \
--field1 repository.git.repositoryUrl --value1 ${2} \

$BAMBOO_SCRIPT -v --action updateRepository --repositoryKey \
com.atlassian.bamboo.plugins.atlassian-bamboo-plugin-git:git \
--name Git --plan "${1}-feature" \
--field1 repository.git.repositoryUrl --value1 ${2} \

echo "Deleting cloned template plans"

$BAMBOO_SCRIPT --action deletePlan --plan "$NEW_PLAN_TEMPLATE_KEY_DEV"

$BAMBOO_SCRIPT --action deletePlan --plan "$NEW_PLAN_TEMPLATE_KEY_FEATURE"

$BAMBOO_SCRIPT --action deletePlan --plan "$NEW_PLAN_TEMPLATE_KEY_MASTER"
