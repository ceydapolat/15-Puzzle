import numpy as np
import random
import pygame
import sys
import math

WHITE = (255,255,255)
GRAY = (81,74,85)

PINK= (246,144,77)
GREEN = (97,239,116)

row_num = 6
column_num = 7

PLAYER = 0
AI = 1

EMPTY = 0
PLAYER_PIECE = 1
AI_PIECE = 2

WINDOW_LENGTH = 4

#res_list = [i for i in range(len(test_list)) if test_list[i] == 3] 
 

def get_available_row(board, col):
	
    board= np.array(board)
    column= board[:,col]
    result=np.where(column==0)[0][0]
    return result
   
            
def print_board(board):
    print(np.flip(board, 0))


def check_win(board, piece):
	# vertical
    for column in range(column_num):
        for row in range(row_num-3):
            if check_adjacent(board, piece, row, column, 1, 0) == 4:
                return True
    # horizontal
    for column in range(column_num-3):
        for row in range(row_num):
            if check_adjacent(board, piece, row, column, 0, 1) == 4:
                return True
	# positive slope diagonal
    for column in range(column_num-3):
        for row in range(row_num-3):
            if check_adjacent(board, piece, row, column, 1, 1) == 4:
                return True 
	#negative slope diagonal
    for column in range(column_num-3):
        for row in range(3, row_num):
            if check_adjacent(board, piece, row, column, -1, 1) == 4:
                return True
    return False

def check_adjacent(board, piece, row, column, delta_row, delta_col):
    count = 0
    for i in range(4):
        current_piece = board[row][column]
        if current_piece == piece:
            count += 1
        row += delta_row
        column += delta_col
    return count

def evaluate_window(window, piece):
    score = 0
    opp_piece = PLAYER_PIECE
    if piece == PLAYER_PIECE:
        opp_piece = AI_PIECE

    if window.count(piece) == 4:
        score += 100
    elif window.count(piece) == 3 and window.count(EMPTY) == 1:
        score += 5
    elif window.count(piece) == 2 and window.count(EMPTY) == 2:
        score += 2

    if window.count(opp_piece) == 3 and window.count(EMPTY) == 1:
        score -= 4

    return score

def get_potencial_foursome(board, piece, row, column):
    score = 0

    return score



def score_position(board, piece, row, column):
    score = 0

    ## Score center column
    center_array = [int(i) for i in list(board[:, column_num//2])]
    center_count = center_array.count(piece)
    score += center_count * 3

    ## Score Horizontal
    for r in range(row_num):
        row_array = [int(i) for i in list(board[r,:])]
        for c in range(column_num-3):
            window = row_array[c:c+WINDOW_LENGTH]
            score += evaluate_window(window, piece)
            score += get_potencial_foursome(board, piece, row, column)

    ## Score Vertical
    for c in range(column_num):
        col_array = [int(i) for i in list(board[:,c])]
        for r in range(row_num-3):
            window = col_array[r:r+WINDOW_LENGTH]
            score += evaluate_window(window, piece)
            score += get_potencial_foursome(board, piece, row, column)

    ## Score posiive sloped diagonal
    for r in range(row_num-3):
        for c in range(column_num-3):
            window = [board[r+i][c+i] for i in range(WINDOW_LENGTH)]
            score += evaluate_window(window, piece)
            score += get_potencial_foursome(board, piece, row, column)

    for r in range(row_num-3):
        for c in range(column_num-3):
            window = [board[r+3-i][c+i] for i in range(WINDOW_LENGTH)]
            score += evaluate_window(window, piece)
            score += get_potencial_foursome(board, piece, row, column)

    return score

def is_terminal_node(board):
    return check_win(board, PLAYER_PIECE) or check_win(board, AI_PIECE) or len(get_valid_locations(board)) == 0

def minimax(board, depth, alpha, beta, maximizingPlayer, row, column):
    valid_locations = get_valid_locations(board)
    is_terminal = is_terminal_node(board)
    if depth == 0 or is_terminal:
        if is_terminal:
            if check_win(board, AI_PIECE):
                return (None, 100000000000000, None)
            elif check_win(board, PLAYER_PIECE):
                return (None, -10000000000000,None)
            else: # Game is over, no more valid moves
                return (None, 0,None)
        else: # Depth is zero
            return (None, score_position(board, AI_PIECE, row, column), None)
    
    if maximizingPlayer:
        value = -math.inf
        column = random.choice(valid_locations)
        for col in valid_locations: 
            row = get_available_row(board, col)
            b_copy = board.copy()
            b_copy[row][col] = AI_PIECE  #move piece to specified location
            
            new_score = minimax(b_copy, depth-1, alpha, beta, False, row, col)[1]
            if new_score > value:
                value = new_score
                column = col
            alpha = max(alpha, value)
            if alpha >= beta:
                break
                
        row = get_available_row(board, column)
        return column, value, row
    
    else: # Minimizing player
        value = math.inf
        column = random.choice(valid_locations)
        for col in valid_locations:
            row = get_available_row(board, col)
            b_copy = board.copy()
            b_copy[row][col] = PLAYER_PIECE  #move piece to specified location
            
            new_score = minimax(b_copy, depth-1, alpha, beta, True, row, col)[1]
            if new_score < value:
                value = new_score
                column = col
            beta = min(beta, value)
            if alpha >= beta:
               break
        row = get_available_row(board, column)
        return column, value,row

def get_valid_locations(board):
    
    top_row= board[-1]
    valid_locations = [i for i in range(len(top_row)) if top_row[i] == 0] 
    return valid_locations


def draw_board(board):
    for c in range(column_num):
        for r in range(row_num):
            pygame.draw.rect(screen, GRAY, (c*100, r*100+100, 100, 100))
            pygame.draw.circle(screen, WHITE, (int(c*100+50), int(r*100+150)), 45)
    
    for c in range(column_num):
        for r in range(row_num):        
            if board[r][c] == PLAYER_PIECE:
                pygame.draw.circle(screen, PINK, (int(c*100+50), 700-int(r*100+50)), 45)
            elif board[r][c] == AI_PIECE: 
                pygame.draw.circle(screen, GREEN, (int(c*100+50), 700-int(r*100+50)), 45)
    pygame.display.update()


board = np.zeros((row_num, column_num))

print_board(board)
game_over = False

pygame.init()

screen = pygame.display.set_mode((700, 700))
draw_board(board)
pygame.display.update()

myfont = pygame.font.SysFont("monospace", 75)

turn = random.randint(PLAYER, AI)

while not game_over:

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()

        if event.type == pygame.MOUSEMOTION:
            pygame.draw.rect(screen, WHITE, (0,0, 700, 100))
            posx = event.pos[0]
            if turn == PLAYER:
                pygame.draw.circle(screen, PINK, (posx, int(100/2)), 45)

        pygame.display.update()

        if event.type == pygame.MOUSEBUTTONDOWN:
            pygame.draw.rect(screen, WHITE, (0,0, 700, 100))
            #print(event.pos)
            # Ask for Player 1 Input
            if turn == PLAYER:
                posx = event.pos[0]
                col = int(math.floor(posx/100))

                if board[row_num-1][col] == 0: #if column is available
                
                    row = get_available_row(board, col)
                    board[row][col] = PLAYER_PIECE  #move piece to specified location

                    if check_win(board, PLAYER_PIECE):
                        label = myfont.render("Player 1 wins!!", 1, PINK)
                        screen.blit(label, (40,10))
                        game_over = True

                    turn = 1 #give turn to the AI

                    print_board(board)
                    draw_board(board)


    # # Ask for Player 2 Input
    if turn == AI and not game_over:                

#         col = random.randint(0, column_num-1)
#         col = pick_best_move(board, AI_PIECE)
            col, minimax_score ,row = minimax(board, 5, -math.inf, math.inf, True, 0, 0)

            board[row][col] = AI_PIECE  #move piece to specified location
            
            if check_win(board, AI_PIECE):
                label = myfont.render("Player 2 wins!!", 1, GREEN)
                screen.blit(label, (40,10))
                game_over = True
                
            print_board(board)
            draw_board(board)

            turn = 0 #give turn to the player
        
    if game_over:
        pygame.time.wait(3000)