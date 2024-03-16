## Project view select [![ci status][badge]][workflow]
[workflow]: https://github.com/tom-power/project-view-select/actions/workflows/gradle.yml
[badge]: https://img.shields.io/github/actions/workflow/status/tom-power/project-view-select/gradle.yml?style=flat-round&logo=github&label=CI%20status

IntelliJ plugin providing actions to select [project tool window views](https://www.jetbrains.com/help/idea/project-tool-window.html#views). Can be used with shortcuts to navigate directly between views. Actions to select `Project` and `All Changed Files` views available currently.

![demo](https://github.com/tom-power/project-view-select/blob/main/assets/projectViewSelectKeymap.png)

| Action                                      | Description                                                 |
|---------------------------------------------|-------------------------------------------------------------|
| Project View Select Project                 | Toggle project window[^1] and select Project view           |
| Project View Select Scope All Changed Files | Toggle project window[^1] and select All Changed Files view |

[^1]: Unless project window is active and requested view is inactive.
