package com.example.dispatcher.dto.group;

import lombok.*;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result{
    private Long id;
    private String title;
    //@Indexed(unique = true)
    private String username;
    private String type;
    private ArrayList<String> active_usernames;
    private String invite_link;
    private Permissions permissions;
    private boolean join_to_send_messages;
    private boolean join_by_request;
    private Photo photo;
}
