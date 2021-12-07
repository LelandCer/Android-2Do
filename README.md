# Android-2Do

2Do is a simple "to-do" list for Android

The main goal is simplicity, not to remake Asana or Jira. Features are intentionally light to
facilitate use. A user only needs to be concerned with "what" and "when"

Planning files are in `/planning`
These include User stories, wireframes, and sprint plans

## UI

The design goal for the app was "notebook paper" and a handwritten list. A script font was chosen to
mimic handwriting, however it currently uses the default Android cursive font, which isn't very
readable. Would replace with a more readable version later

5 Screens:

- Splash
- ToDoLists: Shows all the ToDoLists. Slide to Delete. "Add New" Fab
- ToDoLists Create/Edit ToDoList
- ToDoTasks: Shows the ToDoList details, and all of the ToDoTasks for it. Slide to Delete. "Add New"
  Fab
- Create/Edit ToDoTask

## Brand

The app name is 2DO, referenced in code as TwoDo.

## Base Models

Uses the ToDo-prefix to avoid collision with List. 2DO/TwoDo is branding, and not connected to these
names

- ToDoTask: A sentence description of what to do, and a status for complete or incomplete
- ToDoList: A named collection of ToDoTasks, with a due date

- Id: Ids are an implementation agnostic identifier for a model. Two models are considered equal if
  their Ids are equal. Uses an Injected IdFactory for making Ids so that different implementations
  can be swapped out.
    - Ultimately UUID was chosen for the backing structure for its simple uniqueness guarantees.
    - For the Db, there's also StringId, that just takes a string and uses it as an Id. This is used
      mostly for de/serialization

## Splash Screen

Uses a default-theme replace strategy for the splash screen. The app theme is set as a drawable with
the splash screen, then immediately set to the actual default theme. This allows the splash screen
to show as soon as the app is launched without any blank moments, and disappear as soon as the UI is
rendered.

## Nav Graph

Uses the navigation controller with a `nav_graph`

## Create/Edit

The create and edit actions have been simplified so that there is only really an Edit action. "New"
is just editing an unsaved, new instance

Currently, since ToDoTasks are literally just a name, there is no Edit action in the UI. To edit a
task, delete and re-make. However, because there is no difference, adding this feature is as simple
as adding an interaction to trigger it.

Note: I ran into a bug with the Material Date Picker, I left the bug handling in the fragment, along
with documentation, so that the handling code wouldn't accidentally be refactored out, and so other
developers would be aware of the bug.

## ToDoListDisplay

This is a bindable interface for displaying the ToDoList as UI strings. Another name for this could
be a presenter, however that name has been a little overloaded, so Display was used so as not to
confuse purpose.

## ToDOViewModel

Since the app is small, a single view model suffices, but it would be straightforward to split this
into multiple.

Use LiveData for exposed data. This was mostly chosen for its speed of implementation, and
simplicity in getting started. The next step would be to replace this with separate state flows.
However at the moment, this change does not provide any benefits, so it has been left as LiveData

## Actions

Actions are schedule-able classes that contain business logic. They are written to be implementation
agnostic, so there are some places where more "omniscient" classes could optimize, but complete
separation is preferred

Right now these use the ActionHandler to execute them. To keep things simple, it runs them on the IO
thread, but more nuanced handlers can easily be created as needed.

## Dagger Hilt

Dagger Hilt is used to manage dependencies through modules. The module approach is preferred because
it allows for dependencies to be created in the packages that provide the implementations, keeping
the bindings in scope.

For example, the database repository implementation is bound this way in the database package,
meaning that everything can be removed simply by deleting said package.

## Repositories

There are conceptually 3 possible "Repositories":

- High speed cache
- Solid storage
- Remote

Remote is not in this version, but it can be added if a server component was introduced.

To facilitate this, there is a base `RepositoryInterface` and two children interfaces, a
`CacheRepositoryInterface`, and a `LocalStorageRepositoryInterface`. The purpose of these children
interfaces is to use Injection to swap individual implementations by creating a bindable Interface.

The end result is 3 repositories per ToDoList and ToDoTask:

- `RoomLocalStorageRepository`: `LocalStorageRepository` This is an implementation of the Repository
  using Room as the local storage solution
- `MemCacheRepository`: `CacheRepository` This just holds the data in memory. It's not really needed
  for this light of an app, but exist more to illustrate purpose
- `MultiRespository`: `Repository` Bad name, but this has the other two repositories injected, and
  then handles the cases for when to use each one.

Note, the two repository Models are completely separate. ToDoList has a HasMany type relationship
with ToDoTask, so it would not be unexpected to receive all of the ToDoTasks from the ToDoList
repository. However in the interest of separation, that is not the case here. This allows for better
data management in a more mature app, since the ToDoListsFragment does not require the ToDoTasks at
all, there's no reason for them to load all of it. In additional, this makes paging easier to add in
the future as well.

## Room

Room was chosen as the Solid Storage solution. It's lightweight and fast for an app of this size.
Database constraints have not been added, but would be in a future release, once Error states are
implemented.

Basic Models were added that mirror ToDoList and ToDoTask, these act as de/serialization classes, so
that the models can be saved into / loaded from the DB. This way, no Room code or annotations needs
to exist outside of the `database.room` package.

Modules also bind the repositories to the `LocalStorageRepository` interface.

## Miscellaneous

`StrikeThroughAnimator` Takes a view and lets it be animated. Used by the ToDoTask list for the "
cross out when completed" animation

`FabActivity` An interface that marks an activity as providing a FAB. Let's the fragment bind an
action to the button if the parent activity implements the interface.

`ToDoListFactory`, `ToDoTaskFactory`: These models have a lot of system generated attributes, such
as Id, createdAt, etc. These factories allow for a consistent way of creating new instances of these
models, without stubbing out constructors everywhere. 