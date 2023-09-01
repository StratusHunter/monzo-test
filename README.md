# Tel Notes - Monzo Code Test for Android Engineers

## Questions

### What were your priorities, and why?

Providing the best balance of polish and stable functionality that I could achieve in the time. If this code went live, although it's not feature
complete and optimized it will work and have a level of polish (e.g. app icon, selection animation, collapsing toolbar) that shouldn't cause negative
reviews or make it appear unfinished.

### If you had another two days, what would you have tackled next?

- Finish the main specification items first so splitting the list into weekly headings.
- Add the favorite's functionality.
- Add error states to the details screen.
- Add refresh to the details screen so if an error occurs the user doesn't have to exit and re-enter.
- Add build types for debug and release.
- Add minifyEnabled to the release build type to compress the release artifact.
- On the details screen add the article title to the toolbar on scrolling upwards.
- Use tabs under the toolbar to split the article list and favourites list on the list screen.
- Add an indicator to the article list items that indicates if they are a favourite or not.

### What would you change about the structure of the code?

The only thing structurally I wasn't a fan of was the ArticlesModule class. Something makes me feel like it should live somewhere else or be more
centralised instead of in the articles folder. It felt like it was doing the right thing but maybe the scoping wasn't quite right for when the project
grows.\
I think the choices of libraries I would have done differently if starting from scratch as I did lose some time adjusting to tools I've not used for a
while. I would have replaced RxJava with Coroutines, XML layouts with Compose,
LiveData with State and Moshi with kotlinx.serialization.

### What bugs did you find but not fix?

I added a Snackbar onto the list screen to inform the user if there was an error loading the feed. This doesn't have the LiveData SingleLiveEvent
workaround to stop this reappearing on Fragment view recreation.

### What would you change about the visual design of the app?

It's basic but that's kind of the point of using standard Material Design. It's a nice baseline to get started. Personally I think I would be trying
to get more emphasis on the imagery for the lists screens as that tends to grab the users eye quicker over the text.

### Approximately how long you spent on this project.

4 Hours

## Tickets Done Notes

- MZO-1: Handle error loading article list. Remove API key from source.
    - Out of the gate the app was crashing on startup. First I added error handling to the ReactiveX streams so if an error did happen it would be
      done gracefully. Then I found the root of the issue was the API key was no longer valid. So I made my own key and for good measure extracted the
      key out into the local.properties so our sensitive information is not stored in the repository.
- MZO-2: Matched Toolbar style to designs.
    - The first thing I noticed was the Toolbar had the incorrect title colour, so I changed that and centralized to a style with the intention of
      using this later on.
- MZO-3: Article list view holder design matching. Ordered article list by date.
    - I felt like the best use of my time was to get the article view holders matching the final design, but I felt the sectioning was a lower
      priority to getting the article detail screen made so left the sectioning for later.
    - In the interim before adding sectioning I sorted the article list by date descending so the newest articles are at the top.
    - Spotted a context leak in the adapter, so I removed the storing of the context from the adapter and used the view holder context where needed
      instead.
- MZO-4: Added app icon
    - If this project was going live after the 4 hour limit then it needed to have an app icon to have a feeling of being complete.
- MZO-5: Added details fragment to display the article.
    - Next step was to add the details screen. First I changed the article list activity into a fragment and made a main activity to act as the entry
      point. I felt like this was a bit of a misstep. I think long term this was the better approach but short term I could have made a second
      activity which would have been much faster to get more functionality done then refactored into fragments later.
    - I then created a nav graph for handling navigation and type safe argument passing between the fragments. Again I think maybe I could have just
      done this manually rather than go through setting up a nav graph for 1 additional view but it felt right at the time thinking in a product
      sense.
    - I also made the mistake of thinking the article content already existed in the Article class without realising there needs to be a second API
      call. So I took a bit of a wrong path making Article parcelable and passing it into the fragment when all I should have passed was the url
      parameter.
    - To recreate the designs I chose to use a CollapsingToolbarLayout I'd never used one before so took a bit of exploration to work out how to get
      it to behave how I wanted it too. I did delay myself by trying to have pull to refresh functionality for if the initial API request failed the
      user could retry while in that screen. This didn't play nicely with AppBarLayout and kept overlapping my NestedScrollView content so had to
      remove that functionality due to running out of time. 