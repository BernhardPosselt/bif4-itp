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
        @dom_invite_users = $ "#invite_window #selected_preview .users ul"
        @dom_invite_groups = $ "#invite_window #selected_preview .groups ul"
        @dom_invite_selected_users = $ "#invite_window #invite_selected .users ul"
        @dom_invite_selected_groups = $ "#invite_window #invite_selected .groups ul"
        # dom elements which we append
        @dom_reg_channel_list = {}
        @dom_reg_stream = {}
        @dom_reg_stream_sidebar_users = {}
        @dom_reg_stream_sidebar_files = {}
        @dom_reg_invite_groups = {}
        @dom_reg_invite_users = {}
        # json data
        @channel_data = {}
        @stream_data = {}
        @user_data = {}
        @group_data = {}
        @stream_sidebar_users_data = {}
        @stream_sidebar_files_data = {}
        @file_data = {}
        # other stuff
        @active_channel = undefined
        @last_msg_user = {}
        @last_msg_class = {}
        @last_post_minute = {}
        @loaded_channels = {}
        @init_channels = {}
        @scrolled_channels = {}
        @max_shown_code_lines = 10
        @notify_audio = $("<audio>")
        @notify_audio.attr("src", "/assets/audio/75639__jobro__attention03.ogg")
        @mimetypes = new MimeTypes()

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
        for id, value of @user_data
            @create_user_dom(id, value)
        for id, value of @group_data
            @create_group_dom(id, value)
        for id, value of @file_data
            @create_file_dom(id, value)
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
        @channel_data[id] = data
        @create_dom(id, data)
        @rewrite_user_group_dom()
        

    # creates an element in the channel list and in the appropriate places
    create_dom: (id, data) ->
        # create channel list item
        channel_data = data
        list_entry = $("<li>")
        list_entry.html( channel_data.name )
        list_entry.bind 'click', =>
            @join_channel(id)
        @dom_reg_channel_list[id] = list_entry
        @dom_channel_list.append(list_entry)
        
        # create the stream div
        stream = $("<div>")
        stream.addClass("stream")
        # create div around content
        stream_field = $("<div>")
        stream_field.addClass("stream_field")
        # create title field
        stream_label = $("<div>")
        stream_label.addClass("stream_name")
        stream_name = $("<span>")
        stream_name.html(channel_data.name)
        stream_name.addClass("name")
        stream_label.append(stream_name)
        # create links for deleting and renaming the channel
        stream_utils = $("<div>")
        stream_utils.addClass("utils")
        change_name = $("<a>")
        change_name.attr("href", "#")
        change_name.addClass("change_channel_name")
        change_name.html("Rename")
        delete_channel = $("<a>")
        delete_channel.attr("href", "#")
        delete_channel.addClass("delete_channel")
        delete_channel.html("Delete")
        stream_topic_change_link = $("<a>")
        stream_topic_change_link.html("Topic")
        stream_topic_change_link.addClass("change_topic")
        stream_topic_change_link.attr("href", "#")
        close_channel = $("<a>")
        close_channel.attr("href", "#")
        close_channel.addClass("close_channel")
        close_channel.html("Close")
        stream_utils.append(delete_channel)
        stream_utils.append(close_channel)
        stream_utils.append(change_name)
        stream_utils.append(stream_topic_change_link)
        stream_label.append(stream_utils)
        # create div data in fielset
        stream_meta = $("<div>")
        stream_meta.addClass("stream_meta")
        stream_meta.html("Topic: ")
        stream_topic = $("<span>")
        stream_topic.addClass("topic")
        stream_topic.html(@linkify_text(channel_data.topic))
        stream_meta.append(stream_topic)
        stream_chat = $("<div>")
        stream_chat.addClass("stream_chat")
        stream_chat.scroll =>
            @scrolled(id)
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
        files_list = $("<ul>")
        files_sidebar.append(files_list)
        @dom_reg_stream_sidebar_files[id] = files_sidebar
        @dom_stream_sidebar_files.append(files_sidebar)
        
        console.log("Created channel " + channel_data.name)        



    # updates an element in the data tree and udpates the dom
    update: (id, data) ->
        @channel_data[id] = data
        @update_dom(id, data)
        

    # updates an element in the channel list and in the appropriate places
    update_dom: (id, data) ->
        channel_list = @dom_reg_channel_list[id]
        channel_list.html( data.name )
        stream = @dom_reg_stream[id]
        stream.children(".stream_field").children(".stream_name").children(".name").html(data.name)
        stream.children(".stream_field").children(".stream_meta").children(".topic").html(@linkify_text(data.topic))
        @rewrite_files_dom(id)
        @rewrite_user_group_dom()
        console.log("Updated channel " + data.name)
        

    # creates a new element in the data tree and udpates the dom
    delete: (id) ->
        # check if we got another channel and if a channel exists, join the
        # first one
        if id == @get_active_channel()
            @join_first_channel()
        delete @channel_data[id]
        delete @stream_data[id]
        delete @stream_sidebar_files_data[id]
        delete @stream_sidebar_users_data[id]
        delete @loaded_channels[id]
        delete @scrolled_channels[id]
        delete @init_channels[id]
        @delete_dom(id)

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
    
    # joins a channel
    join_channel: (channel_id) -> 
        # check if we need to get data to init the stream
        if @loaded_channels[channel_id] == undefined
            msg = 
                type: "join"
                data: 
                    channel: channel_id
            @main_manager.send_websocket(msg)      
            @loaded_channels[channel_id] = true
        # remove unread flag    
        list_entry = @dom_reg_channel_list[channel_id]
        list_entry.removeClass("unread")
        # fade out inactive ones and fade in active one
        active_channel_id = @get_active_channel()
        if active_channel_id == undefined
            @dom_reg_stream[channel_id].fadeIn "fast"
            @dom_reg_stream_sidebar_users[channel_id].fadeIn "fast"
            @dom_reg_stream_sidebar_files[channel_id].fadeIn "fast"
        else if channel_id != active_channel_id
            @dom_reg_stream[active_channel_id].fadeOut "fast", =>
                @dom_reg_stream[channel_id].fadeIn "fast"
            @dom_reg_stream_sidebar_users[active_channel_id].fadeOut "fast", =>
                @dom_reg_stream_sidebar_users[channel_id].fadeIn "fast"
            @dom_reg_stream_sidebar_files[active_channel_id].fadeOut "fast", =>
                @dom_reg_stream_sidebar_files[channel_id].fadeIn "fast"
        @active_channel = channel_id            
        console.log("joined channel " + @channel_data[@active_channel].name)
    
    
     
################################################################################
# Streams                           
################################################################################
    
    # initializes a stream with data
    init_stream: (data) ->
        for id, stream_data of data
            @create_stream(id, stream_data)    
            @init_channels[id] = true    

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
        # check if we have to add an unread flag to a channel the user is not in
        if @init_channels[channel_id] and channel_id != @get_active_channel()
            list_entry = @dom_reg_channel_list[channel_id]
            list_entry.addClass("unread")
        # add dom
        line = $("<div>")
        line.addClass("line")
        user = $("<div>")
        user.addClass("user")
        date = $("<div>")
        date.addClass("date")
        msg = $("<div>")
        msg.addClass("message")
        if data.type == "text"
            # check msg contains @PrenameLastname and highlight if true
            current_user = @user_data["" + @active_user]
            highlight_string = "@" + current_user.prename + current_user.lastname
            if data.message.indexOf(highlight_string) != -1
                line.addClass("highlight")
                @notify(channel_id)
            # replace known links, smileys etc
            msg.html(@sugar_text(data.message))
        else
            code_container = $("<div>")
            code_container.addClass("code_container")
            code = $("<pre>")
            code.html(data.message)
            code.addClass("brush: " + data.type)
            # check if code has more than X lines, hide it
            if data.message.split("\n").length > @max_shown_code_lines
                console.log "hid line"
                show_code = $("<a>")
                show_code.html("Show/Hide Code")
                show_code.attr("href", "#")
                show_code.addClass("show_code")
                show_code.click ->
                    code_container.slideToggle "fast"
                    return false;
                msg.append(show_code)
                # dont display code
                code_container.css("display", "none")
            code_container.append(code)
            msg.append(code_container)
        # convert unixtimestamp to date
        date_string = new Date(data.date)
        year = date_string.getFullYear()
        month = date_string.getMonth()
        day = date_string.getDay()
        hours = date_string.getHours()
        minutes = date_string.getMinutes()
        seconds = date_string.getSeconds()
        # add preceding zeros when < 10
        month = if month < 10 then "0" + month else month
        day = if day < 10 then "0" + day else day
        hours = if hours < 10 then "0" + hours else hours
        minutes = if minutes < 10 then "0" + minutes else minutes
        seconds = if seconds < 10 then "0" + seconds else seconds
        year_span = $("<span>")
        year_span.html(year + "-" + month + "-" + day)
        year_span.addClass("year")
        time_span = $("<span>")
        time_span.html(hours + ":" + minutes)
        time_span.addClass("time")
        date.append(year_span)
        date.append(time_span) 
        # get username
        user_data = @get_user(data.user_id)
        user_name = " " + user_data.prename + " " + user_data.lastname
        user.html(user_name)
        # check if we have to alternate the class for setting
        # the background of the message
        if @last_msg_user[channel_id] == undefined
            @last_msg_class[channel_id] = 0
        if @last_msg_user[channel_id] == data.user_id
            msg.addClass("line" + @last_msg_class[channel_id])
            line.addClass("line" + @last_msg_class[channel_id])
            # if the last message was from the last user, dont write his
            # name again
            user.html("")
        else
            @last_msg_class[channel_id] = (@last_msg_class[channel_id] + 1) % 2
            msg.addClass("line" + @last_msg_class[channel_id])
            line.addClass("line" + @last_msg_class[channel_id])
        @last_msg_user[channel_id] = data.user_id
        # only append date if the last minute wasnt the same as this one
        if @last_post_minute[channel_id] == undefined
            @last_post_minute[channel_id] = -1
        if @last_post_minute[channel_id] != minutes
            @last_post_minute[channel_id] = minutes
            date_line = $("<div>")
            date_line.addClass("line")
            date_line.addClass("date_line" + @last_msg_class[channel_id])
            date_user = $("<div>")
            date_user.addClass("user")
            #date_user.html(user_name)
            date_line.append(date_user)
            date_line.append(date)
            stream.append(date_line)
        line.append(user)
        line.append(msg)
        stream.append(line)
        # highlight only when type is not text
        if data.type != "text"
            SyntaxHighlighter.highlight()
        #scroll to the bottom of the div at start or when users moves scroll down
        if @scrolled_channels[channel_id] == true or @scrolled_channels[channel_id] == undefined
            @scroll_to_bottom(channel_id)


    # decides if a new message scrolls to the stream to the bottom
    scrolled: (channel_id) ->
        stream = @dom_reg_stream[channel_id].children(".stream_field").children(".stream_chat")
        if @scrolled_channels[channel_id] == undefined
            @scrolled_channels[channel_id] = true
        scrollBottom = stream.height() + stream.scrollTop()
        if scrollBottom == stream.prop("scrollHeight")
            @scrolled_channels[channel_id] = true
            console.log("autoscroll activated")
        else
            @scrolled_channels[channel_id] = false
            console.log("autoscroll deactivated")
            
            
    # scrolls a stream to the bottom
    scroll_to_bottom: (channel_id) ->
        stream = @dom_reg_stream[channel_id].children(".stream_field").children(".stream_chat")
        stream.scrollTop(stream.prop("scrollHeight")); 
        

    # wraps links in <a> tags, pictures in <img> tags 
    # msg: the message we search
    # return: the final message
    sugar_text: (msg) ->
        # first place all urls in <a> tags
        msg = @linkify_text(msg)
        # add smileys
        smileys = new Smileys()
        for key, smile of smileys.get_smileys()
            img = '<img width="50" height="50" alt="' + key + '" src="' + smileys.get_smiley(key) + '" />'
            msg = msg.replace(" " + key + " ", " " + img + " ")
            msg = msg.replace(key + "<br />", img + '<br />')
            end_line_regex = new RegExp( "(.*)" + @_regex_esc(key) + "$", "g")
            msg = msg.replace(end_line_regex, "$1" + img)
            start_line_regex = new RegExp( "^" + @_regex_esc(key) + "(.*)$", "g")
            msg = msg.replace(start_line_regex, img + "$1")
        # now replace all images in <a> tags with <img> tags
        pictures = ["png", "jpg", "jpeg", "gif"]
        for pic in pictures
            # put a br before and after the image
            pic_regex = new RegExp('<a href="(.*\.' + @_regex_esc(pic) + ')">(.*)<\/a>', "gim")
            msg = msg.replace(pic_regex, '<br/><a href="$1"><img alt="$1" src="$1" /></a><br/>')
        # replace youtube links with embedded video
        yt_regex = /<a href=".*youtube.com\/watch\?v=([0-9a-zA-Z_-]{11}).*">.*<\/a>/gi
        msg = msg.replace(yt_regex, '<br/><iframe width="560" height="315" src="http://www.youtube.com/embed/$1" frameborder="0" allowfullscreen></iframe><br/>')
        return msg
        
    # replaces links and emails with html links
    linkify_text:(msg) ->
        url_regex = /\b((?:https?|ftp):\/\/[a-z0-9+&@#\/%?=~_|!:,.;-]*[a-z0-9+&@#\/%=~_|-])/gi
        pseudo_url_regex = /(^|[^\/])(www\.[\S]+(\b|$))/gi
        email_regex = /\w+@[a-zA-Z_]+?(?:\.[a-zA-Z]{2,6})+/gi
        msg = msg.replace(url_regex, '<a href="$1">$1</a>')
        msg = msg.replace(pseudo_url_regex, '$1<a href="http://$2">$2</a>')
        msg = msg.replace(email_regex, '<a href="mailto:$&">$&</a>')    
        return msg

################################################################################
# Groups and Users
################################################################################

    # this method rewrites the dom for places where updating is complicated, so
    # every create, update or delete, the complete dom is being rewriten
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
        @create_group_dom(id, data)
    
    create_group_dom: (id, data) ->
        list_entry = $("<li>")
        list_entry.html(data.name)
        list_entry.click =>
            @toggle_select_invite_group(id)
        @dom_reg_invite_groups[id] =
            dom: list_entry
            selected: false
        @dom_invite_groups.append(list_entry)
        
    update_group: (id, data) ->
        @group_data[id] = data
        @rewrite_user_group_dom()
        @update_group_dom(id, data)

    # updates the dom for a group
    update_group_dom: (id, data) ->
        list_entry = @dom_reg_invite_groups[id].dom
        list_entry.html(data.name)

    delete_group: (id) ->
        delete @group_data[id]
        @rewrite_user_group_dom()
        @delete_group_dom(id)

    # deletes the group dom entries
    delete_group_dom: (id) ->
        @dom_reg_invite_groups[id].dom.remove()

    # moves the user into the selected area or moves him back if hes already
    # in it
    toggle_select_invite_group: (id) ->
        list_entry = @dom_reg_invite_groups[id]
        dom_elem = list_entry.dom
        if list_entry.selected
            list_entry.selected = false
            dom_elem.detach()
            @dom_invite_groups.append(dom_elem)
        else
            list_entry.selected = true        
            dom_elem.detach()
            @dom_invite_selected_groups.append(dom_elem)

################################################################################
# Users                           
################################################################################

    # sets the initial data array
    init_active_user: (data) ->
        @active_user = data.id
        @callback_init()

    # sets the initial data array
    init_user: (@user_data) ->
        @callback_init()

    # receives the json input and decides which actions to perform
    input_user: (data, actions) ->
        for id, method of actions
            switch method
                when "create" then @create_user(id, data[id])
                when "update" then @update_user(id, data[id])
                when "delete" then @delete_user(id)       

    # creates the dom for one user
    create_user: (id, data) ->
        @user_data[id] = data
        @rewrite_user_group_dom()
        @create_user_dom(id, data)
    
    
    # this creates only the dom in the invite dialogue
    create_user_dom: (id, data) ->
        list_entry = $("<li>")
        list_entry.html(data.prename + " " + data.lastname)
        list_entry.click =>
            @toggle_select_invite_user(id)
        @dom_reg_invite_users[id] =
            dom: list_entry
            selected: false
        @dom_invite_users.append(list_entry)
    
    # updates the user data
    update_user: (id, data) ->
        @user_data[id] = data
        @rewrite_user_group_dom()
        @update_user_dom(id, data)
        
    # updates the dom for a user
    update_user_dom: (id, data) ->
        list_entry = @dom_reg_invite_users[id].dom
        list_entry.html(data.prename + " " + data.lastname)
        
    # deletes the user
    delete_user: (id) ->
        delete @user_data[id]
        @rewrite_user_group_dom()
        @delete_user_dom(id)
        
    # deletes the users dom entries
    delete_user_dom: (id) ->
        @dom_reg_invite_users[id].dom.remove()
        
    # returns the user data
    get_user: (id) ->
        return @user_data[id]
    
    # moves the user into the selected area or moves him back if hes already
    # in it
    toggle_select_invite_user: (id) ->
        list_entry = @dom_reg_invite_users[id]
        dom_elem = list_entry.dom
        if list_entry.selected
            list_entry.selected = false
            dom_elem.detach()
            @dom_invite_users.append(dom_elem)
        else
            list_entry.selected = true        
            dom_elem.detach()
            @dom_invite_selected_users.append(dom_elem)

################################################################################
# Files                           
################################################################################    
    
    # initializes a stream with data
    init_file: (@file_data) ->
    
    # receives input for a stream
    input_file: (data, actions) ->
        for id, method of actions
            switch method
                when "create" then @create_file(id, data[id])
                when "update" then @update_file(id, data[id])
                when "delete" then @delete_file(id)       

    # create file does not add any dom data since the file belongs to a chanel
    # who has the files list. he will receive an update as well to display the 
    # file
    create_file: (id, data) ->
        @file_data[id] = data

    update_file: (id, data) ->
        @file_data[id] = data        
        
    delete_file: (id) ->
        delete @file_data[id]    
    
    # rewrites all files of all channels
    @rewrite_files: () ->
        for channel_id, data of @channel_data
            @rewrite_files_dom(channel_id)
    
    # rewrites all files of one channel
    rewrite_files_dom: (channel_id) ->
        files_ul = @dom_reg_stream_sidebar_files[channel_id].children("ul")
        files_ul.empty()
        for file_id in @channel_data[channel_id].files
            file_id += ""
            file = @file_data[file_id]
            list_entry = $("<li>")
            list_entry.css("background-image", @mimetypes.get_mimetype_icon_path(file.type))
            file_name = $("<a>")
            file_name.addClass("name")
            file_name.html(file.name)
            # TODO: add link to the file
            file_name.attr("href")
            file_size = $("<span>")
            file_name.addClass("size")
            file_size.html(@_kb_to_human_readable(file.size, 2))
            list_entry.append(file_name)
            list_entry.append(file_size)
            files_ul.append(list_entry)
            

################################################################################
# Other
################################################################################

    # checks all users in the active channel for autocompletion of the name
    complete_name: (val) ->
        # get the last part behind the at
        words = val.split("@")
        if words.length == 0
            return val
        word = words[words.length-1]
        if word.length < 2
            return val
        words.pop()
        channel = @channel_data[@get_active_channel()]
        # generate userlist
        user_list = {}
        for user_id, data of @user_data
            groups = data.groups
            for group_id in groups
                if parseInt(group_id) in channel.groups
                    user_list[user_id] = true
            if parseInt(user_id) in channel.users
                user_list[user_id] = true
        # now loop through users
        for user_id, bool of user_list
            user = @user_data[user_id]
            name = user.prename + user.lastname
            # only autocomplete if unique
            matches = 0
            ret = ""
            if @_starts_with(name, word)
                matches += 1
                # rebuild the original message
                for item in words
                    ret += item + "@"
                ret += name
            if matches == 1
                return ret
        return val
    
    
    # moves all users and groups from selected to unselected    
    reset_invite_selection: ->
        for id, elems of @dom_reg_invite_users
            if elems.selected
                @toggle_select_invite_user(id)
        for id, elems of @dom_reg_invite_groups
            if elems.selected
                @toggle_select_invite_group(id)


    # returns all selected and groups and users in the invite window
    get_invite_selection: ->
        users = []
        groups = []
        for id, elems of @dom_reg_invite_users
            if elems.selected
                users.push(parseInt(id))
        for id, elems of @dom_reg_invite_groups
            if elems.selected
                @toggle_select_invite_group(id)
                groups.push(parseInt(id))
        data = 
            users: users
            groups: groups
        return data
        
        
    notify: (channel_id) ->
        if @init_channels[channel_id]
            @notify_audio[0].play()
        

################################################################################
# utilities
################################################################################

    # checks if a word starts with a word
    _starts_with: (word, needle) ->
        word = word.toLowerCase()
        needle = needle.toLowerCase()
        return word.indexOf(needle) == 0

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
        
    # escapes chars when put into regex
    _regex_esc: (string) ->
        return (string+'').replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1")
        
        
    # returns kilobyte to a human readable format
    _kb_to_human_readable: (kilobytes, precision) ->
        megabyte = 1024;
        gigabyte = megabyte * 1024;
        terabyte = gigabyte * 1024;
   
        if kilobytes >= megabyte and kilobytes < gigabyte
            return (kilobytes / megabyte).toFixed(precision) + ' MB'
        else if kilobytes >= gigabyte and kilobytes < terabyte
            return (kilobytes / gigabyte).toFixed(precision) + ' GB'
        else if kilobytes >= terabyte
            return (kilobytes / terabyte).toFixed(precision) + ' TB'
        else
            return kilobytes + ' KB'
