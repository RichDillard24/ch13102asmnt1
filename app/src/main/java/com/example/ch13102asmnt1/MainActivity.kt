package com.example.ch13102asmnt1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// CHALLENGE 7 SESSION 1
fun getAgeGroup(age: Int): String {
    return when {
        age < 13 -> "Child"
        age in 13..17 -> "Teenager"
        age in 18..59 -> "Adult"
        else -> "Senior"
    }
}

// Challange 8

fun addFriend(friendList: MutableList<String>, newFriend: String) {
    if(!friendList.contains(newFriend)) {
        friendList.add(newFriend)
    }
}

//Challenge 8

fun removeFriend(friendList: MutableList<String>, friendName: String){
        friendList.remove(friendName)

}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserProfileScreen()
        }
    }
}

@Composable
fun UserProfileScreen() {
    var name by remember { mutableStateOf("John Smith") }
    var age by remember { mutableIntStateOf(30) }
    val birthday = "1996/05/25"
    val address by remember {mutableStateOf("123 Example St") }
    var username by remember { mutableStateOf("@smith_j") }
    var like by remember { mutableIntStateOf(0) }
    val isVerified = false
    // CHALLENGE 3 SESSION 1
    val friends = remember { mutableStateListOf("Ana", "Eddy", "Chelsea", "Max", "Karen") }
    val ageGroup = getAgeGroup(age)

    Surface {
        ProfileContent(
            name,
            age,
            birthday,
            address,
            username,
            likesCount = like,
            isVerified,
            ageGroup,
            friends = friends,
            // CHALLENGE 4 SESSION 1
            onLike = { like ++},
            onChangeUsername = { username = "@newUser123"},
            onAddFriend = { addFriend(friends, "Rich")},
            onRemoveFriend = { removeFriend(friends, "karen")}
        )
    }
}

@Composable
fun ProfileContent (
    name: String,
    age: Int,
    birthday: String,
    address: String,
    username: String,
    likesCount: Int,
    isVerified: Boolean,
    ageGroup: String,
    friends: List<String>,
    onLike: () -> Unit,
    onChangeUsername: () -> Unit,
    onAddFriend: () -> Unit,
    onRemoveFriend: () -> Unit
){
    var showFriends by remember { mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(20.dp, 35.dp, 20.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\uD83D\uDE03",
                    style = androidx.compose.material3.MaterialTheme.typography.displayMedium
                )
                Text(text = name)
                Text(text = username)
            }
        }

        Text(text = "User Profile",
            modifier = Modifier.padding(20.dp))
        Text(text = "Name: $name",
            modifier = Modifier.padding(5.dp))
        // CHALLENGE 5 SESSION 1
        Text(text = "Age: $age",
            modifier = Modifier.padding(5.dp))
        Text(text = "Birthday: $birthday",
            modifier = Modifier.padding(5.dp))
        Text(text = "Address: $address",
            modifier = Modifier.padding(5.dp))
        Text(text = "Username: $username",
            modifier = Modifier.padding(5.dp))
        Text(text = "Likes: $likesCount",
            modifier = Modifier.padding(5.dp))
        Text(text = "Verified: ${if (isVerified) "✅" else "❌"}")
        Text(text = "Age Group: $ageGroup",
            modifier = Modifier.padding(5.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = onLike) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Like")
            }
            Text(text = "$likesCount")
        }


        Row {
           // Button(onClick = onLike) {
           //     Text("Like")
           // }
            // CHALLENGE 6 SESSION 1
            Button(onClick = onChangeUsername) {
                Text("Change Username")
            }
        }

        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp,0.dp,20.dp)

        ){
            Button(onClick = onRemoveFriend,
                modifier = Modifier.weight(1f)){
                Text("Remove Friend")
            }
            Button(onClick = onAddFriend,
                modifier = Modifier.weight(1f)){
                Text("Add Friend")
            }
        }
        Button(onClick = {showFriends = !showFriends} ) {
            Text(if(showFriends)"Hide friends" else "show Friends")
        }

        if (showFriends){
        Text(text ="Friends (${friends.size}):",
            modifier = Modifier.padding(5.dp))
        friends.forEach { friend ->
            Text(
                text = friend,
                modifier = Modifier.padding(5.dp)
            )
        }
        }
    }
}



//PREVIEW COMPOSABLE FUNCTION
@Preview(showBackground = true)
@Composable
fun PreviewUserProfile() {
    UserProfileScreen()
}