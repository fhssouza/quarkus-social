package com.souzatech.quarkussocial.rest;

import com.souzatech.quarkussocial.domain.model.Post;
import com.souzatech.quarkussocial.domain.model.User;
import com.souzatech.quarkussocial.repository.PostRepository;
import com.souzatech.quarkussocial.repository.UserRepository;
import com.souzatech.quarkussocial.rest.dto.CreatePostRequest;
import com.souzatech.quarkussocial.rest.dto.PostResponse;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final UserRepository userRepository;
    private final PostRepository repository;

    @Inject
    public PostResource(
            UserRepository userRepository,
            PostRepository repository){
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @POST
    @Transactional
    public Response savePost(
            @PathParam("userId") Long userId, CreatePostRequest request){

        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);
//        post.setDateTime(LocalDateTime.now());
        repository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }


//        PanacheQuery<Post> query = repository.find("user", user);
//        List<Post> list = query.list();

        var query = repository.find(
                "user", Sort.by("dateTime", Sort.Direction.Descending), user);
        var list = query.list();

        var postResponseList = list.stream()
//                .map(post -> PostResponse.fromEntity(post))
                .map(PostResponse::fromEntity) //metodo de referencia
                .collect(Collectors.toList());

        return Response.ok(postResponseList).build();
    }

}
