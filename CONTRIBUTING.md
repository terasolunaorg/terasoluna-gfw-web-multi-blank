# How to contribute to the Development of terasoluna-gfw-web-multi-blank repository.

This document describes how to contribute the terasoluna-gfw-web-multi-blank updates.

The terasoluna-gfw-web-multi-blank is a [Maven](https://maven.apache.org/) based blank java projects template for a quick start.
See [Building a Project with Maven](https://maven.apache.org/run-maven/index.html)

Contribution procedures are follows:


## Create a new issue

Create a new issue from [here](https://github.com/terasolunaorg/terasoluna-gfw-web-multi-blank/issues/new?body=%23%23%20Description%0D%0A%28%2A%2ARequired%2A%2A%3A%20Please%20write%20issue%20description%29%0D%0A%0D%0A%23%23%20Possible%20Solutions%0D%0A%28Optional%3A%20Please%20write%20solutions%20of%20this%20issue%20you%20think%29%0D%0A%0D%0A%23%23%20Affects%20Version%2Fs%0D%0A%28%2A%2ARequired%2A%2A%3A%20Please%20select%20affected%20versions%29%0D%0A%2A%205.0.0.RELEASE%0D%0A%2A%201.0.2.RELEASE%0D%0A%0D%0A%23%23%20Fix%20Version%2Fs%0D%0A%28To%20be%20written%20later%20by%20project%20member%29%0D%0A%0D%0A%23%23%20Issue%20Links%0D%0A%28Optional%3A%20Please%20link%20to%20related%20issues%29%0D%0A%2A%20%23%7Bissue%20no%7D%0D%0A%2A%20or%20external%20url) for contributing(bug report, improvement or new content), and get an issue number(tracking id).

> **Note: Supported language**
>
> English only.

* Write the contribution overview into the title area.
* Write the contribution detail into the comment area.

 e.g.)
 ```
 ## Description
 In pom.xml file, there is a mistake in the below sentence.

 `"<artifactId>projctName</artifactId>"`

 ## Possible Solutions
 Modify to `"<artifactId>projectName</artifactId>"`

 ## Affects Version/s
 * 5.0.0.RELEASE
 * 1.0.2.RELEASE

 ## Fix Version/s
 (To be written later by project member)

 ## Issue Links
 * https://github.com/terasolunaorg/terasoluna-gfw-web-multi-blank/issues/999
 ```

## Fork a repository

Fork the `terasolunaorg/terasoluna-gfw-web-multi-blank` into your account repository of GitHub.

* Click a "Fork" button on GitHub web user interface.


## Clone a repository

Clone a forked repository into your local machine.


e.g.)

```
git clone https://github.com/{your account}/terasoluna-gfw-web-multi-blank.git
```


## Create a work branch

Create a work branch on the master branch into your local repository.

> **Note: Recommended work branch name**
>
> issues/{issue number}_{short description}

e.g.)

```
git checkout master
git checkout -b issues/999_typo-in-POM
```


## Modify the terasoluna-gfw-web-multi-blank

Modify the terasoluna-gfw-web-multi-blank for contributing, and confirm if other projects using the terasoluna-gfw-web-multi-blank needs modification.


## Build projects and execute tests

Build all projects using the [Maven](https://maven.apache.org/), and execute tests.

```
cd {root directory of your local repository}
mvn clean install -f pom.xml
```
If a maven build fails or there is an error(s) in the application then modify the source code to remove the failure(s)/error(s).

Deploy the projectName-web project to application server, and access to "http://{Application Server IP}:{Port}/projectName-web/" on web browser.

Then, develop a sample application and confirm the execution result as needed.
If the result fails, modify the source code again.


## Commit the modification

Commit the modification.

> **Note: Commit comment format**
>
> "{modification overview} #{issue number}"

> **Note: Supported language**
>
> English only.

e.g.)

```
git commit -a -m "Fixes typos in POM #999"
```


## Push the work branch

Push the work branch to the GitHub.

e.g.)

```
git push origin issues/999_typo-in-POM
```


## Create a pull request

Create a pull request via GitHub web user interface.
For details, refer to the [GitHub document-Creating a pull request-](https://help.github.com/articles/creating-a-pull-request/).

> **Note: Supported language**
>
> English only.

* Write the modification overview into the title area and add the #{issue number} at its end. (Default is commit comment or work branch name)
* Write the modification detail into the comment area. (If needed)
* Include the issue number(`#{issue number}` format) to track the modification into the comment area.

e.g.)

| Area | Content |
| ----- | --------- |
| Title | Fixes typos in POM #999 |
| Comment | Please review #999 . |
