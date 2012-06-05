$(document).ready ->

    load_admin_divs = (hash) ->
        switch hash
            when "#users" then fade_out_visible_divs $("#admin_site_users")
            when "#groups" then fade_out_visible_divs $("#admin_site_groups")
            when "#channels" then fade_out_visible_divs $("#admin_site_channels")
            
    fade_out_visible_divs = (fadeInElement) ->
        $("#admin_site > div:visible").fadeOut "fast", ->
            fadeInElement.fadeIn()
    
    filter_table_rows = (search_string) ->   
        $("#admin_site > div:visible .data_table tr").each ->
            visible = false
            $(@).children("td").each ->
                if $(@).html().indexOf(search_string) != -1
                    visible = true
            if visible
                $(@).show()
            else
                $(@).hide()    

    # if hash is set, load the divs
    if window.location.hash
        load_admin_divs window.location.hash
    
    # bind tab links
    $("#admin_tabs #admin_tabs_users").click ->
        load_admin_divs("#users")
    $("#admin_tabs #admin_tabs_groups").click ->
        load_admin_divs("#groups")
    $("#admin_tabs #admin_tabs_channels").click ->
        load_admin_divs("#channels")
    # bind search form
    $(".searchbox").keyup ->
        filter_table_rows $(@).val()
        
    $("#admin_site .data_table").tablesorter(); 
        
