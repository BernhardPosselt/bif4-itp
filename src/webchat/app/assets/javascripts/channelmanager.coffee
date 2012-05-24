###
Manages all channel objects in the channellist
###

class ChannelManager

    constructor: (@callback_init, @main_manager) ->
        # dom parent elements to which we append
        @dom_channel_list = $ "#channels ul"
        @dom_group_list = $ "#groups"
        @dom_stream = $ "#streams"
        @dom_stream_sidebar_users = $ "#info_sidebar #channel_users"
        @dom_stream_sidebar_files = $ "#info_sidebar #channel_files"
        # dom elements which we append
        @dom_reg_channel_list = {}
        @dom_reg_stream = {}
        @dom_reg_stream_sidebar_users = {}
        @dom_reg_stream_sidebar_files = {}
        # json data
        @channel_data = {}
        @stream_data = {}
        @user_data = {}
        @group_data = {}
        @stream_sidebar_users_data = {}
        @stream_sidebar_files_data = {}
        # other stuff
        @active_channel = undefined
        @last_msg_user = {}
        @last_msg_class = {}
        @last_post_minute = {}
        @loaded_channels = {}


    # sets the initial data array
    init: (@channel_data) ->
        @callback_init()

        
    # writes initalized data into the ui
    init_ui:() ->
        @dom_channel_list.empty()
        @dom_stream.empty()
        @dom_stream_sidebar_files.empty()
        # create dom for channel list
        for id, value of @channel_data
            @create_dom(id, value)
        # create dom for users and groups
        @rewrite_user_group_dom()
        # create dom for first channel
        @join_first_channel()


    # decides what to do with the input
    # data: the data array
    # actions: the actions array
    input: (data, actions) ->
        for id, method of actions
            switch method
                when "create" then @create(id, data[id])
                when "update" then @update(id, data[id])
                when "delete" then @delete(id)       


    # creates a new element in the data tree and udpates the dom
    create: (id, data) ->
        @data[id] = data
        @create_dom(id)
        

    # creates an element in the channel list and in the appropriate places
    create_dom: (id) ->
        # create channel list item
        channel_data = @channel_data[id]
        list_entry = $("<li>")
        list_entry.html( channel_data.name )
        list_entry.bind 'click', =>
            @join_channel(id)
        @dom_reg_channel_list[id] = list_entry
        @dom_channel_list.append(list_entry)
        
        # create the stream div
        stream = $("<div>")
        stream.addClass("stream")
        # create fieldset around content
        stream_field = $("<fieldset>")
        stream_field.addClass("stream_field")
        stream_label = $("<legend>")
        stream_label.addClass("stream_name")
        stream_label.html(channel_data.name)
        # create div data in fielset
        stream_meta = $("<div>")
        stream_meta.addClass("stream_meta")
        stream_meta.html(channel_data.topic)
        stream_chat = $("<div>")
        stream_chat.addClass("stream_chat")
        # now build dom to add
        stream_field.append(stream_label)
        stream_field.append(stream_meta)
        stream_field.append(stream_chat)
        stream.append(stream_field)
        @dom_reg_stream[id] = stream
        @dom_stream.append(stream)
        
        # create stream sidebar
        users_sidebar = $("<div>")
        users_sidebar.addClass("users")
        @dom_reg_stream_sidebar_users[id] = users_sidebar
        @dom_stream_sidebar_users.append(users_sidebar)
        
        files_sidebar = $("<div>")
        files_sidebar.addClass("files")
        @dom_reg_stream_sidebar_files[id] = files_sidebar
        @dom_stream_sidebar_files.append(files_sidebar)
        
        console.log("Created channel " + channel_data.name)        


    # updates an element in the data tree and udpates the dom
    update: (id, data) ->
        @data[id] = data
        @update_dom(id)
        

    # updates an element in the channel list and in the appropriate places
    update_dom: (id) ->
        channel_data = @data[id]
        channel_list = @dom_reg_channel_list[id]
        channel_list.html( channel_data.name )
        stream = @dom_reg_stream[id]
        stream.children(".stream_field").children(".stream_name").html(channel_data.name)
        stream.children(".stream_field").children(".stream_meta").html(channel_data.topic)
        # TODO: update sidebar files
        console.log("Updated channel " + channel_data.name)
        

    # creates a new element in the data tree and udpates the dom
    delete: (id) ->
        delete @channel_data[id]
        delete @stream_data[id]
        delete @stream_sidebar_files_data[id]
        delete @stream_sidebar_users_data[id]
        delete @loaded_channels[id]
        @delete_dom(id)
        # check if we got another channel and if a channel exists, join the
        # first one 
        @join_first_channel()

    
    # deletes an item from the dom     
    delete_dom: (id) ->
        # remove channel list entry
        channel_entry = @dom_reg_channel_list[id]
        console.log("Removed channel " + channel_entry.html())
        channel_entry.remove()
        delete @dom_reg_channel_list[id]
        # remove stream
        stream = @dom_reg_stream[id]
        stream.remove()
        delete @dom_reg_stream[id]
        # remove users list
        users = @dom_reg_stream_sidebar_files[id]
        delete @dom_reg_stream_sidebar_files[id]
        # remove files list
        files = @dom_reg_stream_sidebar_files[id]
        delete @dom_reg_stream_sidebar_files[id]

    
    # get active stream
    get_active_channel: () ->
        return @active_channel                 
    
    
    # joins the first channel in the list or displays nothing
    join_first_channel: () ->
        console.log("joined first channel")
        if @_get_dict_size(@channel_data) > 0
            for id, value of @channel_data
                @join_channel(id)
                break
        else
            console.log("no channel found, can not join initial channel")
            # TODO: display message that no channel is being found
              
    
    # joins a channel
    join_channel: (@active_channel) -> 
        # check if we need to get data to init the stream
        if @loaded_channels[@active_channel] == undefined
            msg = 
                type: "join"
                data: 
                    channel: @active_channel
            @main_manager.send_websocket(msg)      
            @loaded_channels[@active_channel] = true
        # remove unread flag    
        list_entry = @dom_reg_channel_list[@active_channel]
        list_entry.removeClass("unread")
        # fade out inactive ones and fade in active one 
        @dom_stream.children(".stream").fadeOut "fast", =>
            @dom_reg_stream[@active_channel].fadeIn "fast"
        @dom_stream_sidebar_users.children(".users").fadeOut "fast", =>
            @dom_reg_stream_sidebar_users[@active_channel].fadeIn "fast"
        @dom_stream_sidebar_files.children(".files").fadeOut "fast", =>
            @dom_reg_stream_sidebar_files[@active_channel].fadeIn "fast"
        console.log("joined channel " + @channel_data[@active_channel].name)
    
    
     
################################################################################
# Streams                           
################################################################################
    
    # initializes a stream with data
    init_stream: (data) ->
        for id, stream_data of data
            @create_stream(id, stream_data)    
    
    
    # receives input for a stream
    input_stream: (data, actions) ->
        # messages can only be created, so ignore the actions array
        for id, msg_data of data
            @create_stream(id, msg_data)

    
    # creates the values for a message
    create_stream: (id, data) ->
        # if channel is loaded append message
        if @loaded_channels[id]
            for key, value of data
                if not @stream_data[id]
                    @stream_data[id] = {}
                @stream_data[id][key] = value
                @create_stream_dom(id, key, value)
        # if channel is not the active one, add the unread class
        if id != @active_channel
            elem = @dom_reg_channel_list[id]
            console.log elem
            if not elem.hasClass("unread")
                elem.addClass("unread")
             
    
    # creates the dom for a message
    create_stream_dom: (channel_id, msg_id) ->
        data = @stream_data[channel_id][msg_id]
        stream = @dom_reg_stream[channel_id].children(".stream_field").children(".stream_chat")
        line = $("<div>")
        line.addClass("line")
        user = $("<div>")
        user.addClass("user")
        date = $("<div>")
        date.addClass("date")
        msg = $("<div>")
        msg.addClass("message")
        if data.type == "text"
            msg.html(@sugar_text(data.message))
        else
            code = $("<pre>")
            code.html(data.message)
            code.addClass("brush: " + data.type)
            msg.append(code)
            # TODO: only show x lines and show link to show rest
        # convert unixtimestamp to date
        date_string = new Date(data.date)
        year = date_string.getFullYear()
        month = date_string.getMonth()
        day = date_string.getDay()
        hours = date_string.getHours()
        minutes = date_string.getMinutes()
        seconds = date_string.getSeconds()
        date_string = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds
        date.html(date_string)
        # get username
        user_data = @get_user(data.user_id)
        user_name = " " + user_data.prename + " " + user_data.lastname
        user.html(user_name + ": ")
        # check if we have to alternate the class for setting
        # the background of the message
        if @last_msg_user[channel_id] == undefined
            @last_msg_class[channel_id] = 0
        if @last_msg_user[channel_id] == data.user_id
            msg.addClass("line" + @last_msg_class[channel_id])
            # if the last message was from the last user, dont write his
            # name again
            user.html("")
        else
            @last_msg_class[channel_id] = (@last_msg_class[channel_id] + 1) % 2
            msg.addClass("line" + @last_msg_class[channel_id])
        @last_msg_user[channel_id] = data.user_id
        # only append date if the last minute wasnt the same as this one
        if @last_post_minute[channel_id] == undefined
            @last_post_minute[channel_id] = -1
        if @last_post_minute[channel_id] != minutes
            @last_post_minute[channel_id] = minutes
            stream.append(date)
        line.append(user)
        line.append(msg)
        stream.append(line)
        # highlight and scroll to the bottom of the div
        SyntaxHighlighter.highlight()
        streamElem = stream[0]
        streamElem.scrollTop = streamElem.scrollHeight;


    # wraps links in <a> tags, pictures in <img> tags 
    # msg: the message we search
    # return: the final message
    sugar_text: (msg) ->
        # first place all urls in <a> tags
        url_regex = /\b((?:https?|ftp):\/\/[a-z0-9+&@#\/%?=~_|!:,.;-]*[a-z0-9+&@#\/%=~_|-])/gim
        pseudo_url_regex = /(^|[^\/])(www\.[\S]+(\b|$))/gim
        email_regex = /\w+@[a-zA-Z_]+?(?:\.[a-zA-Z]{2,6})+/gim
        msg = msg.replace(url_regex, '<a href="$1">$1</a>')
        msg = msg.replace(pseudo_url_regex, '$1<a href="http://$2">$2</a>')
        msg = msg.replace(email_regex, '<a href="mailto:$&">$&</a>')
        # now replace all images in <a> tags with <img> tags
        pictures = ["png", "jpg", "jpeg", "gif"]
        for pic in pictures
            pic_regex = new RegExp('<a href="(.*\.' + pic + ')">(.*)<\/a>', "gim")
            msg = msg.replace(pic_regex, '<a href="$1"><img src="$1" /></a>')
        return msg
        

################################################################################
# Groups and Users
################################################################################

    rewrite_user_group_dom: ->
        console.log("rewriting groups and users")
        @dom_group_list.empty()
        @dom_stream_sidebar_users.children(".users").empty()
        # loop through all users and sort them by prename + space + lastname
        sorted_users = {}
        for id, data of @user_data
            user_name = data.prename + " " + data.lastname
            sorted_users[user_name] = id
        sorted_users = @_sort_by_keys(sorted_users)
        
        # now loop through all users and push them into their groups
        groups = {}
        all_users = new Array()
        for name, id of sorted_users
            user = @user_data[id]
            for group_id in user.groups
                # if group array does not exist
                if groups[group_id] == undefined
                    groups[group_id] = new Array()
                groups[group_id].push(id)
            all_users.push(id)
            
        # finally iterate over all groups and create their dom
        for group_id, users of groups
            heading = $("<h1>")
            heading.html(@group_data[group_id].name)
            user_list = $("<ul>")
            for user_id in users
                user = @user_data[user_id]
                user_entry = $("<li>")
                user_entry.html(user.prename + " " + user.lastname)
                if user.online == true
                    user_entry.addClass("online")
                else
                    user_entry.addClass("offline")
                user_list.append(user_entry)                
            @dom_group_list.append(heading)
            @dom_group_list.append(user_list)    
            
        # create group with all users
        user_list = $("<ul>")
        heading = $("<h1>")
        heading.html("All users")
        for user_id in all_users
            user = @user_data[user_id]
            user_entry = $("<li>")
            user_entry.html(user.prename + " " + user.lastname)
            if user.online == true
                user_entry.addClass("online")
            else
                user_entry.addClass("offline")
            user_list.append(user_entry)
        @dom_group_list.append(heading)
        @dom_group_list.append(user_list) 
        
        # create groups in the stream users sidebar
        for channel_id, channels of @channel_data
            dom_users = @dom_reg_stream_sidebar_users[channel_id]
            for group_id in channels.groups
                group = @group_data[group_id]
                heading = $("<h1>")
                heading.html(group.name)
                user_list = $("<ul>")
                for user_id in groups[group_id]
                    user = @user_data[user_id]
                    user_entry = $("<li>")
                    user_entry.html(user.prename + " " + user.lastname)
                    if user.online == true
                        user_entry.addClass("online")
                    else
                        user_entry.addClass("offline")
                    user_list.append(user_entry)
                           
                dom_users.append(heading)    
                dom_users.append(user_list)
             # create users in stream users sidebar
             heading = $("<h1>")
             heading.html("Other users")
             user_list = $("<ul>")   
             for user_id in channels.users
                user = @user_data[user_id]
                user_entry = $("<li>")
                user_entry.html(user.prename + " " + user.lastname)
                if user.online == true
                    user_entry.addClass("online")
                else
                    user_entry.addClass("offline")
                user_list.append(user_entry)       
                dom_users.append(heading)    
                dom_users.append(user_list)                   
  
        

################################################################################
# Groups                           
################################################################################

    # sets the initial data array
    init_group: (@group_data) ->
        @callback_init()

    input_group: (data, actions) ->
        for id, method of actions
            switch method
                when "create" then @create_group(id, data[id])
                when "update" then @update_group(id, data[id])
                when "delete" then @delete_group(id)       

    create_group: (id, data) ->
        @group_data[id] = data
        @rewrite_user_group_dom()
    
    update_group: (id, data) ->
        @group_data[id] = data
        @rewrite_user_group_dom()

    delete_group: (id) ->
        delete @group_data[id]
        @rewrite_user_group_dom()

################################################################################
# Users                           
################################################################################

    # sets the initial data array
    init_user: (@user_data) ->
        @callback_init()

    input_user: (data, actions) ->
        for id, method of actions
            switch method
                when "create" then @create_user(id, data[id])
                when "update" then @update_user(id, data[id])
                when "delete" then @delete_user(id)       

    create_user: (id, data) ->
        @user_data[id] = data
        @rewrite_user_group_dom()
    
    update_user: (id, data) ->
        @user_data[id] = data
        @rewrite_user_group_dom()
        
    delete_user: (id) ->
        delete @user_data[id]
        @rewrite_user_group_dom()
        
    get_user: (id) ->
        return @user_data[id]
    

################################################################################
# Files                           
################################################################################    
    
    # initializes a stream with data
    init_file: (data) ->
        #@streams.init(data)    
    
    
    # receives input for a stream
    input_file: (data, actions) ->
        for id, method of actions
            switch method
                when "create" then @create_file(id, data[id])
                when "update" then @update_file(id, data[id])
                when "delete" then @delete_file(id)       

    create_file: (id, data) ->
    
    update_file: (id, data) ->

    delete_file: (id) ->
    
    
################################################################################
# utilities
################################################################################

    # sorts a hashmap by keys
    _sort_by_keys: (dict) ->
        sortedKeys = new Array()
        sortedObj = {}
        
        for key of dict
            sortedKeys.push(key)
            
        sortedKeys.sort()
        
        for key in sortedKeys
            sortedObj[key] = dict[key]
            
        return sortedObj


    # returns the size of a dictionairy 
    _get_dict_size: (dict) ->
        size = 0
        for key of dict
            if dict.hasOwnProperty(key)
                size++
        return size
