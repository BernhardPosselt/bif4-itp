###
This handels all managers and the reloading of json data
###

class MainManager

    # constructor
    # interval: the update interval
    constructor: (interval) ->
        console.log("setting up manager")
        # declare managers
        @users = new UserManager()
        @channels = new ChannelManager()
        @groups = new GroupManager()
        @files = new FileManager()

        # declare update urls
        @url_channels = "json/channels/"
        @url_users = "json/users/"
        @url_groups = "json/groups/"
        @url_files = "json/files/"
        @url_stream = "json/stream/"

        # update
        @interval = interval * 1000
        @init_update()

    # Intitializes the update interval to call the update method
    init_update: () ->
        console.log("setting update interval to " + @interval)
        @timer = setInterval =>
            @update()
        , @interval
        @update()

    # triggers a json update
    update: () ->
        console.log("updating")
        @users.update(@json_query(@url_users))
        @channels.update(@json_query(@url_channels))
        @groups.update(@json_query(@url_groups))
        @files.update(@json_query(@url_files))

    json_query: (url) ->
        console.log("querying " + url)
        $.getJSON url, (data) ->
            return data